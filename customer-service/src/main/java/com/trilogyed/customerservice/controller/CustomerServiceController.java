package com.trilogyed.customerservice.controller;

import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerServiceController {

    @Autowired
    CustomerService service;
    @Autowired
    public CustomerServiceController(CustomerService service) {
        this.service = service;
    }

    //add customer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
       return service.addCustomer(customer);
    }

    //get customer
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id) {
        return service.findCustomer(id);
    }
    //get list of all customers
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return service.findAllCustomers();
    }

    //update customer
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody @Valid Customer customer) throws Exception {
        if(customer.getId() == 0) {
            customer.setId(id);
            if(id != customer.getId()) {
                throw new IllegalArgumentException("Customer ID on path must match the ID in the customer object");
            }
            service.updateCustomer(customer);
        }
    }

    //delete customer
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        service.deleteCustomer(id);
    }



}
