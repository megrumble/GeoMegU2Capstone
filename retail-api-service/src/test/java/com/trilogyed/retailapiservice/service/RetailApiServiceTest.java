package com.trilogyed.retailapiservice.service;

import com.trilogyed.retailapiservice.models.*;
import com.trilogyed.retailapiservice.util.feign.*;
import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import org.junit.Before;
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

        OrderItem item = mock(OrderItem.class);


        doReturn(1).when(item.getProductId());
        doReturn(product).when(productClient).createProduct(product1);
        doReturn(product).when(productClient).getProductById(1);
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

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);
        inventoryList.add(inventory2);

        doReturn(inventory).when(inventoryClient).createInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventoryById(1);
        doReturn(inventoryList).when(inventoryClient).getAllInventories();
        doReturn(null).when(inventoryClient).getInventoryById(4);
    }
    public void setUpInvoiceClientMock(){

        List<InvoiceViewModel> invoiceList = new ArrayList<>();
        List<InvoiceItem> invoiceItemsInvoice1 = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItemsInvoice1.add(invItem2);


        InvoiceViewModel invoice1 = new InvoiceViewModel();
        invoice1.setInvoiceId(1);
        invoice1.setCustomerId(12);
        invoice1.setPurchaseDate(LocalDate.of(2019, 9,1));
        invoice1.setInvoiceItemsList(invoiceItemsInvoice1);

        InvoiceViewModel invoice2 = new InvoiceViewModel();
        invoice2.setCustomerId(12);
        invoice2.setPurchaseDate(LocalDate.of(2019, 9, 1));
        invoice2.setInvoiceItemsList(invoiceItemsInvoice1);

        invoiceList.add(invoice1);

        doReturn(invoice1).when(invoiceClient).createInvoice(invoice2);
        doReturn(invoice1).when(invoiceClient).getInvoice(1);
        doReturn(invoiceList).when(invoiceClient).getAllInvoices();
        doReturn(invoiceList).when(invoiceClient).getInvoicesByCustomer(12);

    }

    public void setUpLevelUpClientMock(){

        List<Member> memberList = new ArrayList<>();

        //add mock information here for tests
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.of(2019, 9, 1));

        Member member2 = new Member();
        member2.setLevelUpId(1);
        member2.setCustomerId(5);
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
        doReturn(member2).when(levelUpClient).getMemberByCustomerId(5);
        doReturn(memberList).when(levelUpClient).getAllMembers();
        doReturn(10).when(levelUpClient).getPointsByCustId(5);


    }
}
