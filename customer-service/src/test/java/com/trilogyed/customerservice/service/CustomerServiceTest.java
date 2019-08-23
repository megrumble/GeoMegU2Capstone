package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)

public class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;
    @Mock
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpCustomerDaoMock();
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
        customer = customerService.addCustomer(customer);
        //test get 1
        Customer fromService = customerService.findCustomer(customer.getId());
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

        List<Customer> customers1 = customerService.findAllCustomers();
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
        customer = customerService.addCustomer(customer);

        customer.setEmail("newEmail");
        customerService.updateCustomer(customer);
        Customer customer1 = new Customer();
        customer1.setId(customer.getId());
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setStreet(customer.getStreet());
        customer1.setCity(customer.getCity());
        customer1.setZip(customer.getZip());
        customer1.setEmail(customer.getEmail());
        customer1.setPhone(customer.getPhone());
        verify(customerDao).updateCustomer(customer1);

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
        customer = customerService.addCustomer(customer);

        customerService.deleteCustomer(customer.getId());
        verify(customerDao).deleteCustomer(customer.getId());
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowRuntimeExceptionWhenCustomerIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no customer with ID " + id);
        // do something that should throw the exception...
        customerService.findCustomer(id);
    }



    private void setUpCustomerDaoMock() {
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

        doReturn(customer).when(customerDao).createCustomer(customer1);
        doReturn(customer).when(customerDao).getCustomerById(1);
        doReturn(customerList).when(customerDao).getAllCustomers();
        doReturn(null).when(customerDao).getCustomerById(4);


    }
}
