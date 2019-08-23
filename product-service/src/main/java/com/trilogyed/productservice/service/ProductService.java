package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.exception.NotFoundException;
import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {

    private ProductDao dao;

    @Autowired
    public ProductService(ProductDao dao) {
        this.dao = dao;
    }
    //add product
    public Product addProduct(Product product) {
        return dao.createProduct(product);
    }
     //find product by id
    public Product findProduct(int id) throws NotFoundException {
        Product product = dao.getProductById(id);
        if (product == null) {
            throw new NotFoundException("There is no product with ID " + id);
        }
        return product;
    }
    //get a list of all products
    public List<Product> findAllProducts() {
        return dao.getAllProducts();
    }
    //update product
    public void updateProduct(Product product) {
        dao.updateProduct(product);
    }
    //delete product
    public void deleteProduct(int id) {
        dao.deleteProductById(id);
    }
}