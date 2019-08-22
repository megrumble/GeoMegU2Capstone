package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = customerDao.getAllCustomers();
        customerList.stream().forEach(c -> customerDao.deleteCustomer(c.getId()));
    }

    @Test
    public void createGetCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = customerDao.createCustomer(customer);

        Customer customer1 = customerDao.getCustomerById(customer.getId());
        assertEquals(customer, customer1);
    }

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
        customer = customerDao.createCustomer(customer);

        customerDao.deleteCustomer(customer.getId());
        Customer customer1 = customerDao.getCustomerById(customer.getId());
        assertNull(customer1);

    }

    @Test
    public void getAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = customerDao.createCustomer(customer);

        customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Lennon");
        customer.setStreet("123 Strawberry Fields");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = customerDao.createCustomer(customer);

        List<Customer> customers = customerDao.getAllCustomers();
        assertEquals(customers.size(), 2);

    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Ringo");
        customer.setLastName("Starr");
        customer.setStreet("123 Penny Lane");
        customer.setCity("Charlotte");
        customer.setZip("12345");
        customer.setEmail("email");
        customer.setPhone("1234567890");
        customer = customerDao.createCustomer(customer);

        customer.setEmail("newEmail");
        customerDao.updateCustomer(customer);

        Customer customer1 = customerDao.getCustomerById(customer.getId());
        assertEquals(customer, customer1);

    }
}
