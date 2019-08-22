package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.InvoiceItem;
import com.trilogyed.invoiceservice.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemDaoJdbcTemplateImpl implements InvoiceItemDao {
    private String INSERT_INVOICE_ITEM =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) " +
                    "values (?, ?, ?, ?)";
    private String SELECT_INVOICE_ITEM_BY_ID =
            "select * from invoice_item where invoice_item_id = ?";
    private String SELECT_ALL_INVOICE_ITEMS =
            "select * from invoice_item";
    private String SELECT_INVOICE_ITEM_BY_INVOICE_ID =
            "select * from invoice_item where invoice_id = ?";
    private String UPDATE_INVOICE_ITEM =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";
    private String DELETE_INVOICE_ITEM =
            "delete from invoice_item where invoice_item_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public InvoiceItemDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
        jdbcTemplate.update(
                INSERT_INVOICE_ITEM,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice()
        );

        invoiceItem.setInvoiceItemId(
                jdbcTemplate.queryForObject(
                        "select last_insert_id()",
                        Integer.class
                )
        );

        return invoiceItem;
    }

    @Override
    public InvoiceItem getInvoiceItemById(int id) {
        try{
            return jdbcTemplate.queryForObject(
                    SELECT_INVOICE_ITEM_BY_ID,
                    this::mapRowToInvoiceItem,
                    id
            );
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        try{
            return jdbcTemplate.query(
                    SELECT_ALL_INVOICE_ITEMS,
                    this::mapRowToInvoiceItem
            );
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id) {
        try{
            return jdbcTemplate.query(
                    SELECT_INVOICE_ITEM_BY_INVOICE_ID,
                    this::mapRowToInvoiceItem,
                    id
            );
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        try {
            jdbcTemplate.update(
                    UPDATE_INVOICE_ITEM,
                    invoiceItem.getInvoiceId(),
                    invoiceItem.getInventoryId(),
                    invoiceItem.getQuantity(),
                    invoiceItem.getUnitPrice(),
                    invoiceItem.getInvoiceItemId()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("No Invoice found for invoice item id " + invoiceItem.getInvoiceId());
        }
    }

    @Override
    @Transactional
    public void deleteInvoiceItem(int id) {
        jdbcTemplate.update(
                DELETE_INVOICE_ITEM,
                id
        );
    }

    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNumber) throws SQLException {
        InvoiceItem invItem = new InvoiceItem();
        invItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invItem.setInvoiceId(rs.getInt("invoice_id"));
        invItem.setInventoryId(rs.getInt("inventory_id"));
        invItem.setQuantity(rs.getInt("quantity"));
        invItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        return invItem;
    }
}
