package com.trilogyed.adminservice.controller;

import com.trilogyed.adminservice.model.Product;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/products")
public class ProductServiceController {

    @Autowired
    AdminServiceLayer service;

    public ProductServiceController(AdminServiceLayer service) {
        this.service = service;
    }
    //add product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product, Principal principal) {
        return service.addProduct(product);
    }

    //get product
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable("id") int id, Principal principal) {
        return service.findProduct(id);
    }

    //get list of all products
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(Principal principal) {
        return service.findAllProducts();

    }
    //update product
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable int id, @RequestBody @Valid Product product, Principal principal) throws Exception{
        if(product.getId() == 0) {
            product.setId(id);
            if(id != product.getId()) {
                throw new IllegalArgumentException("Product ID on path must match the ID in the product object");
            }
            service.updateProduct( id, product);
        }
    }
    //delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id, Principal principal) {
        service.deleteProduct(id);
    }
}
