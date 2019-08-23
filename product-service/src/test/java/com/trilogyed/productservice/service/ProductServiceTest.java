package com.trilogyed.productservice.service;

import com.trilogyed.productservice.dao.ProductDao;
import com.trilogyed.productservice.exception.NotFoundException;
import com.trilogyed.productservice.model.Product;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    ProductService productService;
    @Mock
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpProductDaoMock();
    }

    @Test
    public void addFindProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        //test add
        product = productService.addProduct(product);
        //test get 1
        Product fromService = productService.findProduct(product.getId());
        assertEquals(product, fromService);
    }
    @Test
    public void findAllProducts() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Game");
        product1.setDescription("Fun");
        product1.setListPrice(new BigDecimal("20.00"));
        product1.setUnitCost(new BigDecimal("5.00"));
        productList.add(product1);


        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Console");
        product2.setDescription("Amazing!");
        product2.setListPrice(new BigDecimal("200.00"));
        product2.setUnitCost(new BigDecimal("45.00"));
        productList.add(product2);

        List<Product> productList1 = productService.findAllProducts();
        assertEquals(productList, productList1);

    }

    //check update
    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productService.addProduct(product);

        product.setListPrice(new BigDecimal("25.00"));
        productService.updateProduct(product);
        Product product1 = new Product();
        product1.setId(product.getId());
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setListPrice(product.getListPrice());
        product1.setUnitCost(product.getUnitCost());
        verify(productDao).updateProduct(product1);

    }
    //check delete
    @Test
    public void deleteProduct() {
        Product product = new Product();
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));
        product = productService.addProduct(product);

        productService.deleteProduct(product.getId());
        verify(productDao).deleteProductById(product.getId());

    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void shouldThrowRuntimeExceptionWhenProductIsNull() throws Exception {
        int id = 4;
        expectedEx.expect(NotFoundException.class);
        expectedEx.expectMessage("There is no product with ID " + id);
        // do something that should throw the exception...
        productService.findProduct(id);
    }

    private void setUpProductDaoMock() {
        Product product = new Product();
        product.setId(1);
        product.setName("Game");
        product.setDescription("Fun");
        product.setListPrice(new BigDecimal("20.00"));
        product.setUnitCost(new BigDecimal("5.00"));

        Product product1 = new Product();
        product1.setName("Game");
        product1.setDescription("Fun");
        product1.setListPrice(new BigDecimal("20.00"));
        product1.setUnitCost(new BigDecimal("5.00"));

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Console");
        product2.setDescription("Amazing!");
        product2.setListPrice(new BigDecimal("200.00"));
        product2.setUnitCost(new BigDecimal("45.00"));

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        doReturn(product).when(productDao).createProduct(product1);
        doReturn(product).when(productDao).getProductById(1);
        doReturn(productList).when(productDao).getAllProducts();
        doReturn(null).when(productDao).getProductById(4);
    }
}
