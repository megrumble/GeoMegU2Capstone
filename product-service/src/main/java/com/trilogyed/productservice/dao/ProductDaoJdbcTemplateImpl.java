package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class ProductDaoJdbcTemplateImpl implements ProductDao{


    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_PRODUCT_SQL =
            "insert into product(product_name, product_description, list_price, unit_cost) values(?, ?, ?, ?)";

    public static final String SELECT_PRODUCT_SQL =
            "select * from product where product_id = ?";

    public static final String SELECT_ALL_PRODUCTS_SQL =
            "select * from product";

    public static final String UPDATE_PRODUCT_SQL =
            "update product set product_name = ?, product_description = ?, list_price = ?, unit_cost = ? where product_id = ?";

    public static final String DELETE_PRODUCT_SQL =
            "delete from product where product_id = ?";

    @Autowired
    public ProductDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Transactional
    @Override
    //add new product
    public Product createProduct(Product product) {
        jdbcTemplate.update(
                INSERT_PRODUCT_SQL,
                product.getName(),
                product.getDescription(),
                product.getListPrice(),
                product.getUnitCost());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        product.setId(id);
        return product;

    }
    //get one product by id
    @Override
    public Product getProductById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_PRODUCT_SQL, this::mapRowToProduct, id);
        } catch (EmptyResultDataAccessException e) {
            //there is no product matching the given id
            return null;
        }
    }

    @Override
    //get a list of all products
    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS_SQL, this::mapRowToProduct);
    }

    @Override
    //update product details
    public void updateProduct(Product product) {

        jdbcTemplate.update(
                UPDATE_PRODUCT_SQL,
                product.getName(),
                product.getDescription(),
                product.getListPrice(),
                product.getUnitCost(),
                product.getId());


    }

    @Override
    //delete product
    public void deleteProductById(int id) {

        jdbcTemplate.update(DELETE_PRODUCT_SQL, id);

    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("product_id"));
        product.setName(rs.getNString("product_name"));
        product.setDescription(rs.getNString("product_description"));
        product.setListPrice(rs.getBigDecimal("list_price"));
        product.setUnitCost(rs.getBigDecimal("unit_cost"));
        return product;
    }






}
