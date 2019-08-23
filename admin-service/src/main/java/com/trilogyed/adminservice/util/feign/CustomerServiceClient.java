package com.trilogyed.adminservice.util.feign;

import com.trilogyed.adminservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/customers")
@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid Customer customer);

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id);

    @GetMapping
    public List<Customer> getAllCustomers();

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable int id, @RequestBody @Valid Customer customer);

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id);


}
