package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.service.RetailApiService;
import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import com.trilogyed.retailapiservice.models.Product;
import com.trilogyed.retailapiservice.viewmodel.RetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/retail")
public class RetailApiController {
    // Retail Endpoints
    @Autowired
    RetailApiService retailService;

    public RetailApiController(RetailApiService retailService) {
        this.retailService = retailService;
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public RetailViewModel submitOrder(@RequestBody RetailViewModel order) {
//        try {
            return retailService.submitOrder(order);
//        } catch (Exception e){
//            throw new IllegalArgumentException
//        }
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        return null;
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET) //can be derived from service layers and existing crud methods in other services
    public List<Product> getProductsInInventory() {
        return null;
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)  //can be derived from service layers and existing crud methods in other services
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        return null;
    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int id) {
        return 0;
    }
}
