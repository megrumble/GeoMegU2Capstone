package com.trilogyed.adminservice.util.feign;

import com.trilogyed.adminservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @PostMapping
    public Product createProduct(@RequestBody @Valid Product product);

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody @Valid Product product);

    @GetMapping
    public List<Product> getAllProducts();

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id);

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id);
}
