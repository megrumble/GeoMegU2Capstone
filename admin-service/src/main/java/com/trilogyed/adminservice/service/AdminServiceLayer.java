package com.trilogyed.adminservice.service;

import com.trilogyed.adminservice.exception.NotFoundException;
import com.trilogyed.adminservice.model.*;
import com.trilogyed.adminservice.util.feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.NoninvertibleTransformException;
import java.util.List;


@Component
public class AdminServiceLayer {

    private ProductServiceClient productClient;

    private CustomerServiceClient customerClient;

    private InventoryServiceClient inventoryClient;

    private InvoiceServiceClient invoiceClient;

    private LevelUpServiceClient levelUpClient;

    @Autowired
    public AdminServiceLayer(ProductServiceClient productClient, CustomerServiceClient customerClient, InventoryServiceClient inventoryClient,
                             InvoiceServiceClient invoiceClient, LevelUpServiceClient levelUpClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
    }
    //add product
    public Product addProduct(Product product) {
        return productClient.createProduct(product);
    }
    //update product
    public  void updateProduct(int id, Product product) {
        productClient.updateProduct( id, product);
    }
    //get product
    public Product findProduct(int id) throws NotFoundException {
        Product product = productClient.getProductById(id);
        if(product == null) {
            throw new NotFoundException("There is no product with ID " + id);
        }
        return product;
    }
    //getAllproducts
    public List<Product> findAllProducts() {
        return productClient.getAllProducts();
    }
    //delete product
    public void deleteProduct(int id) {
        productClient.deleteProduct(id);
    }
    //add customer
    public Customer addCustomer(Customer customer) {
        return customerClient.createCustomer(customer);
    }
    //update customer
    public void updateCustomer(int id, Customer customer) {
        customerClient.updateCustomer(id, customer);
    }
    //get customer
    public Customer findCustomer (int id)  throws NotFoundException{
        Customer customer = customerClient.getCustomerById(id);
        if (customer == null) {
            throw new NotFoundException("There is no customer with ID " + id);
        }
        return customer;

    }
    //get all customers
    public List<Customer> findAllCustomers() {
        return customerClient.getAllCustomers();
    }
    //delete customer
    public void deleteCustomer(int id) {
        customerClient.deleteCustomer(id);
    }
    //add inventory
    public Inventory addInventory(Inventory inventory) {
        return inventoryClient.createInventory(inventory);
    }
    //update inventory
    public void updateInventory(int id, Inventory inventory) {
        inventoryClient.updateInventory(id, inventory);
    }
    //get inventory
    public Inventory findInventorybyId(int id) throws NotFoundException {
        Inventory inventory = inventoryClient.getInventoryById(id);
        if (inventory == null) {
            throw new NotFoundException("There is no inventory with ID " + id);
        }
        return inventory;
    }
    //get all inventories
    public List<Inventory> findAllInventories() {
        return inventoryClient.getAllInventories();
    }
    //delete inventory
    public void deleteInventory(int id) {
        inventoryClient.deleteInventory(id);
    }
    //add member
    public Member addMember(Member member) {
        return levelUpClient.createMember(member);
    }
    //update member
    public void updateMember(int id, Member member) {
        levelUpClient.updateMember(id, member);
    }
    //get member
    public Member findMember(int id) {
        return levelUpClient.getMember(id);
    }
    //get all members
    public List<Member> findAllMembers() {
        return levelUpClient.getAllMembers();
    }
    //get points by customer
    public int findPointsByCustomerId(int id) {
        return levelUpClient.getPointsByCustId(id);
    }
    //delete member
    public void deleteMember(int id) {
        levelUpClient.deleteMember(id);
    }
    //add invoice
    public InvoiceViewModel addInvoice(InvoiceViewModel ivm) {
        return invoiceClient.createInvoice(ivm);
    }
    //update invoice
    public void updateInvoice(InvoiceViewModel ivm) {
        invoiceClient.updateInvoice(ivm);
    }
    //get invoice
    public InvoiceViewModel findInvoice(int id) {
        return invoiceClient.getInvoice(id);
    }
    //get all invoices
    public List<InvoiceViewModel> findAllInvoices() {
        return invoiceClient.getAllInvoices();
    }
    //get invoices by customer
    public List<InvoiceViewModel> findInvoicesByCustomer(int id) {
        return invoiceClient.getInvoicesByCustomer(id);
    }
    //delete invoice
    public void deleteInvoice(int id) {
        invoiceClient.deleteInvoice(id);
    }


}
