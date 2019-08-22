package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class CustomerDaoJdbcImpl implements CustomerDao{

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_CUSTOMER_SQL =
            "insert into customer(first_name, last_name, street, city, zip, email, phone) values(?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_CUSTOMER_SQL =
            "select * from customer where customer_id = ?";

    public static final String SELECT_ALL_CUSTOMERS_SQL =
            "select * from customer";

    public static final String UPDATE_CUSTOMER_SQL =
            "update customer set first_name = ?, last_name = ?, street = ?, city = ?, zip = ?, email = ?, phone = ? where customer_id = ?";

    public static final String DELETE_CUSTOMER_SQL =
            "delete from customer where customer_id = ?";
    @Autowired
    public CustomerDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        jdbcTemplate.update(
                INSERT_CUSTOMER_SQL,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        customer.setId(id);
        return customer;
    }

    @Override
    public Customer getCustomerById(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_CUSTOMER_SQL, this::mapRowToCustomer, id);
        } catch (EmptyResultDataAccessException e) {
            //there is no customer matching the given id
            return null;
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS_SQL, this::mapRowToCustomer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        jdbcTemplate.update(
                UPDATE_CUSTOMER_SQL,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getStreet(),
                customer.getCity(),
                customer.getZip(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getId());


    }

    @Override
    public void deleteCustomer(int id) {

        jdbcTemplate.update(DELETE_CUSTOMER_SQL, id);

    }

    private Customer mapRowToCustomer(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customer_id"));
        customer.setFirstName(rs.getNString("first_name"));
        customer.setLastName(rs.getNString("last_name"));
        customer.setStreet(rs.getNString("street"));
        customer.setCity(rs.getNString("city"));
        customer.setZip(rs.getNString("zip"));
        customer.setEmail(rs.getNString("email"));
        customer.setPhone(rs.getNString("phone"));
        return customer;
    }
}
