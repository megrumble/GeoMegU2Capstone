package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    Product createProduct(Product product);

    Product getProductById(int id);

    List<Product> getAllProducts();

    void updateProduct(Product product);

    void deleteProductById(int id);
}
