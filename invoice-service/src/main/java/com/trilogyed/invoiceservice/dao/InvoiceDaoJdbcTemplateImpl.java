package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.Invoice;
import com.trilogyed.invoiceservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {
    private String INSERT_INVOICE =
            "insert into invoice (customer_id, purchase_date) " +
                    "values (?, ?)";
    private String SELECT_INVOICE_BY_ID =
            "select * from invoice where invoice_id = ?";
    private String SELECT_ALL_INVOICES =
            "select * from invoice";
    private String SELECT_INVOICES_BY_CUSTOMER_ID =
            "select * from invoice where customer_id = ?";
    private String UPDATE_INVOICE =
            "update invoice set customer_id = ? , purchase_date = ? where invoice_id = ? ";
    private String DELETE_INVOICE =
            "delete from invoice where invoice_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {

        jdbcTemplate.update(
                INSERT_INVOICE,
                invoice.getCustomerId(),
                invoice.getPurchaseDate()
        );

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        invoice.setInvoiceId(id);

        return invoice;
    }

    @Override
    public Invoice getInvoice(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_INVOICE_BY_ID,
                    this::mapRowToInvoice,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        try {
            return jdbcTemplate.query(
                    SELECT_ALL_INVOICES,
                    this::mapRowToInvoice
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Invoice> getInvoicesByCustomerId(int id) {
        try {
            return jdbcTemplate.query(
                    SELECT_INVOICES_BY_CUSTOMER_ID,
                    this::mapRowToInvoice,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateInvoice(Invoice invoice) {
        try {
            jdbcTemplate.update(
                    UPDATE_INVOICE,
                    invoice.getCustomerId(),
                    invoice.getPurchaseDate(),
                    invoice.getInvoiceId()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("No Invoice found for invoice id " + invoice.getInvoiceId());
        }
    }

    @Override
    public void deleteInvoice(int id) {
        jdbcTemplate.update(
                DELETE_INVOICE,
                id
        );

    }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNumber) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setCustomerId(rs.getInt("customer_id"));
        invoice.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());

        return invoice;
    }
}
