package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.models.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodel.RetailViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RetailApiServiceTest {
    @InjectMocks
    RetailApiService service;
    @Mock
    ProductServiceClient productClient;
    @Mock
    CustomerServiceClient customerClient;
    @Mock
    InventoryServiceClient inventoryClient;
    @Mock
    InvoiceServiceClient invoiceClient;
    @Mock
    LevelUpServiceClient levelUpClient;
    @Mock
    RabbitTemplate rabbitTemplate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpCustomerClientMock();
        setUpLevelUpClientMock();
        setUpInvoiceClientMock();
        setUpProductClientMock();
        setUpInventoryClientMock();
        setupRabbitTemplateMock();
    }
    @Test
    public void submitOrder() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setProductId(1);
        item.setListPrice(new BigDecimal("20.00"));
        item.setQuantity(2);
        orderItems.add(item);


        RetailViewModel rvm = new RetailViewModel();
        rvm.setCustomerId(1);
        rvm.setPurchaseDate(LocalDate.of(2019,9,1));
        rvm.setOrderItems(orderItems);
    }
    @Test
    public void findInvoice() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInvoiceId(1);
        invItem.setInventoryId(1);
        invItem.setQuantity(2);
        invItem.setUnitPrice(new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm.setInvoiceItemsList(invoiceItems);


        InvoiceViewModel fromService = service.getInvoiceById(1);

        assertEquals(ivm, fromService);

//        List<InvoiceViewModel> invoiceViewModels = service.getAllInvoices();
//        assertEquals(invoiceViewModels.size(), 1);

    }
    @Test
    public void findAllInvoices() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInvoiceId(1);
        invItem.setInventoryId(1);
        invItem.setQuantity(2);
        invItem.setUnitPrice(new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm.setInvoiceItemsList(invoiceItems);

        List<InvoiceItem> invoiceItems2 = new ArrayList<>();

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInvoiceId(2);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal("3.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItems2.add(invItem2);

        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(2);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm1.setInvoiceItemsList(invoiceItems2);

        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        invoiceViewModels.add(ivm);
        invoiceViewModels.add(ivm1);

        List<InvoiceViewModel> ivmList = service.getAllInvoices();

        assertEquals(invoiceViewModels, ivmList);

    }
    @Test
    public void findInvoicesByCustomer() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInvoiceId(1);
        invItem.setInventoryId(1);
        invItem.setQuantity(2);
        invItem.setUnitPrice(new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm.setInvoiceItemsList(invoiceItems);

        List<InvoiceItem> invoiceItems2 = new ArrayList<>();

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInvoiceId(2);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal("3.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItems2.add(invItem2);

        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(2);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm1.setInvoiceItemsList(invoiceItems2);

        List<InvoiceViewModel> invoiceViewModels = new ArrayList<>();
        invoiceViewModels.add(ivm);
        invoiceViewModels.add(ivm1);

        List<InvoiceViewModel> ivmByCustomer = service.getInvoicesByCustId(12);

        assertEquals(ivmByCustomer, invoiceViewModels);
    }
    @Test
    public void findProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        Product fromService = service.getProductById(product.getId());
        assertEquals(product, fromService);


    }
    @Test
    public void findProductsInInventory() {
        Product product = new Product();
        product.setId(1);
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Console");
        product2.setDescription("Amazing!");
        product2.setListPrice(new BigDecimal("200.00"));
        product2.setUnitCost(new BigDecimal("45.00"));

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        List<Product> products = service.getProductsInInventory();
        assertEquals(products, productList);

    }

    //helper methods
    private void setUpProductClientMock() {
        Product product = new Product();
        product.setId(1);
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        Product product1 = new Product();
        product1.setName("Game");
        product1.setDescription("Fun");
        product1.setListPrice(new BigDecimal("20.00"));
        product1.setUnitCost(new BigDecimal("5.00"));

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Console");
        product2.setDescription("Amazing!");
        product2.setListPrice(new BigDecimal("200.00"));
        product2.setUnitCost(new BigDecimal("45.00"));

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        doReturn(product).when(productClient).createProduct(product1);
        doReturn(product).when(productClient).getProductById(1);
        doReturn(product2).when(productClient).getProductById(2);
        doReturn(productList).when(productClient).getAllProducts();
        doReturn(null).when(productClient).getProductById(4);
    }

    private void setUpCustomerClientMock() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");

        Customer customer1 = new Customer();
        customer1.setFirstName("Ringo");
        customer1.setLastName("Starr");
        customer1.setStreet("123 Penny Lane");
        customer1.setCity("Charlotte");
        customer1.setZip("12345");
        customer1.setEmail("email");
        customer1.setPhone("1234567890");

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Lennon");
        customer2.setStreet("123 Strawberry Fields");
        customer2.setCity("Charlotte");
        customer2.setZip("12345");
        customer2.setEmail("email");
        customer2.setPhone("1234567890");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerList.add(customer2);

        doReturn(customer).when(customerClient).createCustomer(customer1);
        doReturn(customer).when(customerClient).getCustomerById(1);
        doReturn(customerList).when(customerClient).getAllCustomers();
        doReturn(null).when(customerClient).getCustomerById(4);
    }
    private void setUpInventoryClientMock() {
        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);

        Inventory inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(10);

        Inventory inventory2 = new Inventory();
        inventory2.setId(2);
        inventory2.setProductId(2);
        inventory2.setQuantity(20);

        Inventory inventory3 = new Inventory();
        inventory2.setProductId(2);
        inventory2.setQuantity(20);



        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        inventoryList.add(inventory2);

        doReturn(inventory).when(inventoryClient).createInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventoryById(1);
        doReturn(inventory2).when(inventoryClient).getInventoryById(2);
        doReturn(inventoryList).when(inventoryClient).getAllInventories();
        doReturn(null).when(inventoryClient).getInventoryById(4);
    }
    public void setUpInvoiceClientMock(){

        List<InvoiceViewModel> invoiceList = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice1 = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice2 = new ArrayList<>();


        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInvoiceId(1);
        invItem.setInventoryId(1);
        invItem.setQuantity(2);
        invItem.setUnitPrice(new BigDecimal("5.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInvoiceId(2);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal("3.00").setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice2.add(invItem2);


        InvoiceViewModel invoice1 = new InvoiceViewModel();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(12);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9,1));
        invoice1.setInvoiceItemsList(invoiceItemsInvoice1);

        InvoiceViewModel invoice2 = new InvoiceViewModel();
        invoice2.setCustomerId(12);
        invoice2.setPurchaseDate(LocalDate.of(2019, 9, 1));
        invoice2.setInvoiceItemsList(invoiceItemsInvoice1);

        InvoiceViewModel invoice3 = new InvoiceViewModel();
        invoice3.setInvoiceId(2);
        invoice3.setCustomerId(12);
        invoice3.setPurchaseDate(LocalDate.of(2019, 9,1));
        invoice3.setInvoiceItemsList(invoiceItemsInvoice2);

        invoiceList.add(invoice1);
        invoiceList.add(invoice3);

        doReturn(invoice1).when(invoiceClient).createInvoice(invoice2);
        doReturn(invoice1).when(invoiceClient).getInvoice(1);
        doReturn(invoice3).when(invoiceClient).getInvoice(2);
        doReturn(invoiceList).when(invoiceClient).getAllInvoices();
        doReturn(invoiceList).when(invoiceClient).getInvoicesByCustomer(12);

    }

    public void setUpLevelUpClientMock(){

        List<Member> memberList = new ArrayList<>();

        //add mock information here for tests
        Member member = new Member();
        member.setCustomerId(1);
        member.setPoints(10);
        member.setMemberDate(LocalDate.of(2019, 9, 1));

        Member member2 = new Member();
        member2.setLevelUpId(1);
        member2.setCustomerId(1);
        member2.setPoints(10);
        member2.setMemberDate(LocalDate.of(2019, 9, 1));
        memberList.add(member2);

        Member member3 = new Member();
        member3.setLevelUpId(2);
        member3.setCustomerId(6);
        member3.setPoints(30);
        member3.setMemberDate(LocalDate.of(2019, 9, 1));
        memberList.add(member3);


        doReturn(member2).when(levelUpClient).createMember(member);
        doReturn(member2).when(levelUpClient).getMember(1);
        doReturn(member2).when(levelUpClient).getMemberByCustomerId(1);
        doReturn(memberList).when(levelUpClient).getAllMembers();
        doReturn(10).when(levelUpClient).getPointsByCustId(5);


    }
    public void setupRabbitTemplateMock() {

    }
}
