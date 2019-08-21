package com.trilogyed.productservice.controller;

import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductServiceController {

    @Autowired
    ProductService service;

    public ProductServiceController(ProductService service) {
        this.service = service;
    }
    //add product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid Product product) {
        return service.addProduct(product);
    }

    //get product
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable("id") int id) {
        return service.findProduct(id);
    }

    //get list of all products
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return service.findAllProoducts();

    }
    //update product
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable int id, @RequestBody @Valid Product product) throws Exception{
        if(product.getId() == 0) {
            product.setId(id);
            if(id != product.getId()) {
                throw new IllegalArgumentException("Product ID on path must match the ID in the product object");
            }
            service.updateProduct(product);
        }
    }
    //delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }


}
