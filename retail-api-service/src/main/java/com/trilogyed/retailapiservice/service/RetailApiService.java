package com.trilogyed.retailapiservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.retailapiservice.exception.NotFoundException;
import com.trilogyed.retailapiservice.models.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.RetailViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetailApiService {
    public static final String EXCHANGE = "level-up-exchange";
    public static final String ROUTING_KEY = "level-up.create.#";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    ProductServiceClient productClient;
    LevelUpServiceClient levelUpClient;
    InvoiceServiceClient invoiceClient;
    CustomerServiceClient customerClient;
    InventoryServiceClient inventoryClient;

    @Autowired
    public RetailApiService(ProductServiceClient productClient,
                            LevelUpServiceClient levelUpClient,
                            InvoiceServiceClient invoiceClient,
                            CustomerServiceClient customerClient,
                            InventoryServiceClient inventoryClient,
                            RabbitTemplate rabbitTemplate) {
        this.productClient = productClient;
        this.levelUpClient = levelUpClient;
        this.invoiceClient = invoiceClient;
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public RetailViewModel submitOrder(RetailViewModel rvm) {
        InvoiceViewModel ivm = new InvoiceViewModel();

        try{
            Customer customer = customerClient.getCustomerById(rvm.getCustomerId());
        } catch (NotFoundException e) {
            throw new NotFoundException("Customer ID is invalid or customer doesn't exist");
        }

        //validate product
        //get product id and unit price
        List<Product> validProductList = new ArrayList<>();

        //adds product to list to check if product exist
        //setting list price to each orderItem in the retailviewmodel
        //if product is not valid it should throw a NotFoundException
        for(OrderItem orderItem: rvm.getOrderItems()){
            try {
                validProductList.add(productClient.getProductById(orderItem.getProductId()));
                orderItem.setListPrice(productClient.getProductById(orderItem.getProductId()).getListPrice());
                if (orderItem.getQuantity() <= 0){
                    throw new IllegalArgumentException("Product quantity can not be less than 1");
                }
            } catch(NotFoundException e) {
                throw new NotFoundException("Invalid product id or product doesn't exist");
            }
        }

        //calculate total order amount
        BigDecimal totalAmount = new BigDecimal(0);

        if(!rvm.getOrderItems().isEmpty()) {
            for (OrderItem item : rvm.getOrderItems()) {
                totalAmount = totalAmount.add(item.getListPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        } else {
            System.out.println("The order items list is empty");
        }

        // check if customer is a level up member
        if(isLevelUpMember(rvm.getCustomerId())) {
            rvm.setPoints(calculatePoints(totalAmount));
            //TODO check for null pointer
            Member member = levelUpClient.getMemberByCustomerId(rvm.getCustomerId());
            member.setPoints(rvm.getPoints());
            sendPointsToQueue(member);
        } else {
            rvm.setPoints(0);
        }


        //get inventory id and quantity for product, then set to invoiceItems
        //verify product order quantity with inventory quantity
        for(OrderItem item: rvm.getOrderItems()) {

            for (Inventory inventory : inventoryClient.getAllInventories()) {
                if (inventory.getProductId() == item.getProductId()){

                    if(item.getQuantity() > inventory.getQuantity()){  //verify order quantity and inventory quantity
                        throw new IllegalArgumentException("Order quantity exceeds inventory quantity");
                    }

                    InvoiceItem invoiceItem = new InvoiceItem();
                    invoiceItem.setInventoryId(inventory.getId());
                    invoiceItem.setQuantity(item.getQuantity());
                    invoiceItem.setUnitPrice(productClient.getProductById(item.getProductId()).getListPrice());
                    ivm.setInvoiceItemsList(new ArrayList<>());
                    ivm.getInvoiceItemsList().add(invoiceItem);

                }
            }
        }


        ivm.setCustomerId(rvm.getCustomerId());
        ivm.setPurchaseDate(LocalDate.now());

        ivm = invoiceClient.createInvoice(ivm);

        rvm.setInvoiceId(ivm.getInvoiceId());
        rvm.setPurchaseDate(LocalDate.now());

        return rvm;
    }

    @HystrixCommand(fallbackMethod = "reliable") //fallbackmethod not implemented yet
    public int getLevelUpPointsByCustomerId(int id){
        return levelUpClient.getPointsByCustId(id);
    }

    public int reliable(int id){
//        return "The Level-Up service is experiencing some technical difficulties, " +
//                "just give us a passing grade and then try again.   -Thanks";
        return 0;
    }

    public InvoiceViewModel getInvoiceById(int id) throws NotFoundException{
        InvoiceViewModel ivm = invoiceClient.getInvoice(id);
        if(ivm == null){
            throw new NotFoundException("No invoice found for id " + id);
        }

        return ivm;
    }

    public List<InvoiceViewModel> getAllInvoices(){
        List<InvoiceViewModel> ivmList = invoiceClient.getAllInvoices();
        if(ivmList.isEmpty()) {
            throw new NotFoundException("No invoices available");
        }

        return ivmList;
    }

    public List<InvoiceViewModel> getInvoicesByCustId(int id){
        List<InvoiceViewModel> ivmList = invoiceClient.getInvoicesByCustomer(id);
        if(ivmList.isEmpty()){
            throw new NotFoundException("No invoices available for customer id " + id);
        }
        return ivmList;
    }

    public List<Product> getProductsInInventory(){
        try {
            List<Inventory> inventoryList = inventoryClient.getAllInventories();
            inventoryList = inventoryList.stream()
                    .filter(inventory -> inventory.getQuantity() > 0).collect(Collectors.toList());

            List<Product> productList = new ArrayList<>();
            for (Inventory inventory : inventoryList) {
                productList.add(productClient.getProductById(inventory.getProductId()));
            }

            return productList;
        } catch (Exception e) {
            throw new NotFoundException("Either no inventory found or no product found");
        }
    }

    public Product getProductById(int id){
        Product product = productClient.getProductById(id);
        if(product == null){
            throw new NotFoundException("No product found for id " + id);
        }
        return product;
    }

    public List<Product> getProductByInvoiceId(int id){
        List<InvoiceItem> invoiceItems = invoiceClient.getInvoice(id).getInvoiceItemsList();
        if(invoiceItems.isEmpty()){
            throw new NotFoundException("No items found for id " + id);
        }
        List<Product> productList = new ArrayList<>();
        for(InvoiceItem item: invoiceItems){
            productList.add(
                    productClient.getProductById(
                            inventoryClient.getInventoryById(
                                    item.getInventoryId()
                            )
                                    .getProductId()
                    )
            );
        }
        return productList;
    }


    private void sendPointsToQueue(Member member) {
        //send points to queue from here
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, member);
        System.out.println("Message Sent");
    }



    private boolean isLevelUpMember(int customerId) {

        List<Member> memberList = levelUpClient.getAllMembers();

        for(Member member: memberList){
            if (member.getCustomerId() == customerId){
                return true;
            }
        }

        return false;
    }

    private int calculatePoints(BigDecimal orderTotal) {

        int points = orderTotal.intValue()/50;
        points *= 10;
        return points;
    }
}
