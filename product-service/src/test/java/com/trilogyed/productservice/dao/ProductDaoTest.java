package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        List<Product> products = productDao.getAllProducts();
        products.stream().forEach(product -> productDao.deleteProductById(product.getId()));
    }

    @Test
    public void createGetProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("25.99"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productDao.createProduct(product);

        Product product1 = productDao.getProductById(product.getId());
        assertEquals(product, product1);
    }

    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("25.99"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productDao.createProduct(product);

        productDao.deleteProductById(product.getId());
        Product product1 = productDao.getProductById(product.getId());
        assertNull(product1);

    }

    @Test
    public void getAllProducts() {

        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("25.99"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productDao.createProduct(product);

        product = new Product();
        product.setName("Console");
        product.setDescription("Awesome");
        product.setListPrice(new BigDecimal("200.00"));
        product.setUnitCost(new BigDecimal("45.00"));
        product = productDao.createProduct(product);

        List<Product> products = productDao.getAllProducts();
        assertEquals(products.size(), 2);
    }

    @Test
    public void updateProduct() {

        Product product = new Product();
        product.setId(1);
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("25.99"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productDao.createProduct(product);

        product.setListPrice(new BigDecimal("35.99"));
        productDao.updateProduct(product);

        Product product1 = productDao.getProductById(product.getId());
        assertEquals(product, product1);
    }
}
