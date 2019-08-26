package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.exception.NotFoundException;
import com.trilogyed.retailapiservice.service.RetailApiService;
import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import com.trilogyed.retailapiservice.models.Product;
import com.trilogyed.retailapiservice.viewmodel.RetailViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/retail")
@CacheConfig(cacheNames = {"orders"})
public class RetailApiController {
    // Retail Endpoints
    @Autowired
    RetailApiService retailService;

    public RetailApiController(RetailApiService retailService) {
        this.retailService = retailService;
    }

    @CachePut(key = "#result.getInvoiceId()")
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public RetailViewModel submitOrder(@Valid @RequestBody RetailViewModel order) {

            return retailService.submitOrder(order);

    }

    @Cacheable
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        InvoiceViewModel ivm = retailService.getInvoiceById(id);
        if(ivm == null){
            throw new NotFoundException("(Controller) No invoice found for id " + id);
        }
        return ivm;
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> ivmList = retailService.getAllInvoices();
        if(ivmList.isEmpty()){
            throw new NotFoundException("No invoices found");
        }
        return ivmList;
    }

    @Cacheable
    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        List<InvoiceViewModel> ivmList = retailService.getInvoicesByCustId(id);
        if (ivmList.isEmpty()){
            throw new NotFoundException("No invoices found for customer id " + id);
        }
        return ivmList;
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET) //can be derived from service layers and existing crud methods in other services
    public List<Product> getProductsInInventory() {
        List<Product> productList = retailService.getProductsInInventory();
        if(productList.isEmpty()){
            throw new NotFoundException("No products found in inventory");
        }
        return productList;
    }

    @Cacheable
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable int id) {
        Product product = retailService.getProductById(id);
        if(product == null){
            throw new NotFoundException("No product found for id " + id);
        }
        return product;
    }

    @Cacheable
    @RequestMapping(value = "/products/invoice/{id}", method = RequestMethod.GET)  //can be derived from service layers and existing crud methods in other services
    public List<Product> getProductByInvoiceId(@PathVariable int id) {
        List<Product> productList = retailService.getProductByInvoiceId(id);
        if(productList.isEmpty()){
            throw new NotFoundException("No products found for invoice id " + id);
        }
        return productList;
    }

    @Cacheable
    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(@PathVariable("id") int id) {
        return retailService.getLevelUpPointsByCustomerId(id);
    }
}
