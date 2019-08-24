package com.trilogyed.adminservice.controller;

import com.trilogyed.adminservice.model.Customer;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RefreshScope
public class CustomerServiceController {

    @Autowired
    AdminServiceLayer service;
    @Autowired
    public CustomerServiceController(AdminServiceLayer service) {
        this.service = service;
    }

    //add customer
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer, Principal principal) {
        return service.addCustomer(customer);
    }

    //get customer
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id, Principal principal) {
        return service.findCustomer(id);
    }
    //get list of all customers
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(Principal principal) {
        return service.findAllCustomers();
    }

    //update customer
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody @Valid Customer customer, Principal principal) throws Exception {
        if(customer.getId() == 0) {
            customer.setId(id);
            if(id != customer.getId()) {
                throw new IllegalArgumentException("Customer ID on path must match the ID in the customer object");
            }
            service.updateCustomer( id, customer);
        }
    }

    //delete customer
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id, Principal principal) {
        service.deleteCustomer(id);
    }
}
