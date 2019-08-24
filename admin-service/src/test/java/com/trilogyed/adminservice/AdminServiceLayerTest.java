package com.trilogyed.adminservice;

import com.trilogyed.adminservice.exception.NotFoundException;
import com.trilogyed.adminservice.model.*;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import com.trilogyed.adminservice.util.feign.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceLayerTest {
    @InjectMocks
    AdminServiceLayer service;
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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpProductClientMock();
        setUpCustomerClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
    }

    @Test
    public void addFindProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        //test add
        product = service.addProduct(product);
        //test get 1
        Product fromService = service.findProduct(product.getId());
        assertEquals(product, fromService);
    }
    @Test
    public void findAllProducts() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Game");
        product1.setDescription("Fun");
        product1.setListPrice(new BigDecimal("20.00"));
        product1.setUnitCost(new BigDecimal("5.00"));
        productList.add(product1);


        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Console");
        product2.setDescription("Amazing!");
        product2.setListPrice(new BigDecimal("200.00"));
        product2.setUnitCost(new BigDecimal("45.00"));
        productList.add(product2);

        List<Product> productList1 = service.findAllProducts();
        assertEquals(productList, productList1);

    }

    //check update
    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = service.addProduct(product);

        product.setListPrice(new BigDecimal("25.00"));
        service.updateProduct(product.getId(), product);
        Product product1 = new Product();
        product1.setId(product.getId());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setListPrice(product.getListPrice());
        product1.setUnitCost(product.getUnitCost());
        verify(productClient).updateProduct(product1.getId(),product1);

    }
    //check delete
    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = service.addProduct(product);

        service.deleteProduct(product.getId());
        verify(productClient).deleteProduct(product.getId());

    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowRuntimeExceptionWhenProductIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no product with ID " + id);
        // do something that should throw the exception...
        service.findProduct(id);
    }

    @Test
    public void addFindCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        //test add
        customer = service.addCustomer(customer);
        //test get 1
        Customer fromService = service.findCustomer(customer.getId());
        assertEquals(customer, fromService);
    }
    //check get all customers
    @Test
    public void findAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("Ringo");
        customer1.setLastName("Starr");
        customer1.setStreet("123 Penny Lane");
        customer1.setCity("Charlotte");
        customer1.setZip("12345");
        customer1.setEmail("email");
        customer1.setPhone("1234567890");
        customers.add(customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("John");
        customer2.setLastName("Lennon");
        customer2.setStreet("123 Strawberry Fields");
        customer2.setCity("Charlotte");
        customer2.setZip("12345");
        customer2.setEmail("email");
        customer2.setPhone("1234567890");
        customers.add(customer2);

        List<Customer> customers1 = service.findAllCustomers();
        assertEquals(customers, customers1);
    }

    //check update
    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = service.addCustomer(customer);

        customer.setEmail("newEmail");
        service.updateCustomer(customer.getId(), customer);
        Customer customer1 = new Customer();
        customer1.setId(customer.getId());
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setStreet(customer.getStreet());
        customer1.setCity(customer.getCity());
        customer1.setZip(customer.getZip());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        verify(customerClient).updateCustomer(customer1.getId(), customer1);

    }

    //check delete
    @Test
    public void deleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = service.addCustomer(customer);

        service.deleteCustomer(customer.getId());
        verify(customerClient).deleteCustomer(customer.getId());
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenCustomerIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no customer with ID " + id);
        // do something that should throw the exception...
        service.findCustomer(id);
    }

    @Test
    public void addFindInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        //test add
        inventory = service.addInventory(inventory);
        //test get 1
        Inventory fromService = service.findInventorybyId(inventory.getId());
        assertEquals(inventory, fromService);

    }
    //check find all
    @Test
    public void findAllInventories() {
        List<Inventory> inventoryList = new ArrayList<>();

        Inventory inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventoryList.add(inventory);

        Inventory inventory1 = new Inventory();
        inventory1.setId(2);
        inventory1.setProductId(2);
        inventory1.setQuantity(20);
        inventoryList.add(inventory1);

        List<Inventory> inventoryList1 = service.findAllInventories();
        assertEquals(inventoryList, inventoryList1);

    }
    //check update
    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = service.addInventory(inventory);

        inventory.setQuantity(20);
        service.updateInventory(inventory.getId(), inventory);
        Inventory inventory1 = new Inventory();
        inventory1.setId(inventory.getId());
        inventory1.setProductId(inventory.getProductId());
        inventory1.setQuantity(inventory.getQuantity());
        verify(inventoryClient).updateInventory(inventory1.getId(), inventory1);
    }
    //check delete
    @Test
    public void deleteInventory() {
        Inventory inventory = new Inventory();
        inventory.setProductId(1);
        inventory.setQuantity(10);
        inventory = service.addInventory(inventory);

        service.deleteInventory(inventory.getId());
        verify(inventoryClient).deleteInventory(inventory.getId());



    }


    @Test
    public void shouldThrowRuntimeExceptionWhenInventoryIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no inventory with ID " + id);
        // do something that should throw the exception...
        service.findInventorybyId(id);
    }

    @Test
    public void addFindMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.of(2019, 9, 1));

        member = service.addMember(member);
        Member member2 = service.findMember(1);

        assertEquals(member, member2);

    }

    @Test
    public void findAllMembers() {
        List<Member> memberList = new ArrayList<>();

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

        List<Member> memberList2 = service.findAllMembers();

        assertEquals(memberList, memberList2);

    }

    @Test
    public void getPointsByCustId() {
        int points1 = 10;

        int points2 = service.findPointsByCustomerId(5);

        assertEquals(points1, points2);

    }

    @Test
    public void updateMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.of(2019, 9, 1));
        member = service.addMember(member);

        member.setPoints(20);

        service.updateMember(member.getLevelUpId(), member);

        verify(levelUpClient).updateMember(any(Integer.class), any(Member.class));
    }

    @Test
    public void deleteMember() {
        Member member = new Member();
        member.setCustomerId(5);
        member.setPoints(10);
        member.setMemberDate(LocalDate.of(2019, 9, 1));
        member = service.addMember(member);

        service.deleteMember(member.getLevelUpId());

        verify(levelUpClient).deleteMember(any(Integer.class));
    }

    @Test
    public void addFindInvoice() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.of(2019, 9,1));
        ivm.setInvoiceItemsList(invoiceItems);

        ivm = service.addInvoice(ivm);

        InvoiceViewModel ivm2 = service.findInvoice(ivm.getInvoiceId());

        assertEquals(ivm, ivm2);

    }

    @Test
    public void findAllInvoices() {
        List<InvoiceViewModel> viewModels = new ArrayList<>();
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);


        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(1);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.of(2019,9,1));
        ivm1.setInvoiceItemsList(invoiceItems);
        viewModels.add(ivm1);


        List<InvoiceViewModel> ivmList = service.findAllInvoices();

        assertEquals(viewModels, ivmList);

    }

    @Test
    public void findInvoiceByCustomerId() {
        List<InvoiceViewModel> viewModels = new ArrayList<>();
        List<InvoiceItem> invoiceItems = new ArrayList<>(); //lists that will be added to InvoiceViewModels

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);

        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(1);
        ivm1.setCustomerId(12);
        ivm1.setPurchaseDate(LocalDate.of(2019,9,1));
        ivm1.setInvoiceItemsList(invoiceItems);
        viewModels.add(ivm1);

        List<InvoiceViewModel> ivmList = service.findInvoicesByCustomer(12);

        assertEquals(ivmList.size(), 1);

    }

    @Test
    public void updateInvoice() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(9);
        invItem.setInventoryId(5);
        invItem.setQuantity(20);
        invItem.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem);

        InvoiceItem invItem2 = new InvoiceItem();
        invItem2.setInvoiceItemId(10);
        invItem2.setInventoryId(3);
        invItem2.setQuantity(12);
        invItem2.setUnitPrice(new BigDecimal(3.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItems.add(invItem2);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(1);
        ivm.setCustomerId(12);
        ivm.setPurchaseDate(LocalDate.of(2019, 9, 1));
        ivm.setInvoiceItemsList(invoiceItems);

        service.addInvoice(ivm);
        ivm.setCustomerId(1);
        service.updateInvoice(ivm);

        verify(invoiceClient).updateInvoice(any(InvoiceViewModel.class));


    }

    @Test
    public void deleteInvoice() {

        service.deleteInvoice(1);
//        verify(invoiceItemDao).deleteInvoiceItem(any(Integer.class));
        verify(invoiceClient).deleteInvoice(any(Integer.class));

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
        doReturn(memberList).when(levelUpClient).getAllMembers();
        doReturn(10).when(levelUpClient).getPointsByCustId(5);


    }


}
