package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerService {

    private CustomerDao dao;

    @Autowired

    public CustomerService(CustomerDao dao) {
        this.dao = dao;
    }

    //add customer
    public Customer addCustomer(Customer customer) {
        return dao.createCustomer(customer);
    }
    //find customer by id
    public Customer findCustomer(int id) throws NotFoundException {
        Customer customer = dao.getCustomerById(id);
        if (customer == null) {
            throw new NotFoundException("There is no customer with ID " + id);
        }
        return customer;
    }
    //get a list of all customers
    public List<Customer> findAllCustomers() {
       return dao.getAllCustomers();
    }
    //update customer details
    public void updateCustomer(Customer customer) {
        dao.updateCustomer(customer);

    }
    //delete customer
    public void deleteCustomer(int id) {
        dao.deleteCustomer(id);
    }
}
