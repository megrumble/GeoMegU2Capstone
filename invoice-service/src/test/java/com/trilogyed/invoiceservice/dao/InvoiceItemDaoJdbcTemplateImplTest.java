package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.Invoice;
import com.trilogyed.invoiceservice.dto.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceItemDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {
        invoiceItemDao.getAllInvoiceItems().stream()
                .forEach(invItem -> invoiceItemDao.deleteInvoiceItem(invItem.getInvoiceItemId()));
    }

    @Test
    public void createGetInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(6);
        invoiceItem1.setUnitPrice(new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem1 = invoiceItemDao.createInvoiceItem(invoiceItem1);

        InvoiceItem invoiceItem2 = invoiceItemDao.getInvoiceItemById(invoiceItem1.getInvoiceItemId());

        assertEquals(invoiceItem1, invoiceItem2);

    }

    @Test
    public void getAllInvoiceItems() {
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        List<InvoiceItem> invoiceItemList1 = invoiceItemDao.getAllInvoiceItems();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(6);
        invoiceItem1.setUnitPrice(new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem1 = invoiceItemDao.createInvoiceItem(invoiceItem1);
        invoiceItemList.add(invoiceItem1);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice.getInvoiceId());
        invoiceItem2.setInventoryId(3);
        invoiceItem2.setQuantity(5);
        invoiceItem2.setUnitPrice(new BigDecimal(7.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem2 = invoiceItemDao.createInvoiceItem(invoiceItem2);
        invoiceItemList.add(invoiceItem2);

        List<InvoiceItem> invoiceItemList2 = invoiceItemDao.getAllInvoiceItems();

        assertEquals(invoiceItemList, invoiceItemList2);
        assertTrue(invoiceItemList1.isEmpty());
    }

    @Test
    public void getInvoiceItemByInvoiceId() {
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        List<InvoiceItem> invoiceItemList1 = invoiceItemDao.getInvoiceItemByInvoiceId(9);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(6);
        invoiceItem1.setUnitPrice(new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem1 = invoiceItemDao.createInvoiceItem(invoiceItem1);
        invoiceItemList.add(invoiceItem1);

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(invoice.getInvoiceId());
        invoiceItem2.setInventoryId(3);
        invoiceItem2.setQuantity(5);
        invoiceItem2.setUnitPrice(new BigDecimal(7.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem2 = invoiceItemDao.createInvoiceItem(invoiceItem2);
        invoiceItemList.add(invoiceItem2);

        List<InvoiceItem> invoiceItemList2 = invoiceItemDao.getInvoiceItemByInvoiceId(invoice.getInvoiceId());

        assertEquals(invoiceItemList, invoiceItemList2);
        assertTrue(invoiceItemList1.isEmpty());

    }

    @Test
    public void updateInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(6);
        invoiceItem1.setUnitPrice(new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem1 = invoiceItemDao.createInvoiceItem(invoiceItem1);

        invoiceItem1.setUnitPrice(new BigDecimal(5.00).setScale(2, RoundingMode.HALF_UP));

        invoiceItemDao.updateInvoiceItem(invoiceItem1);

        InvoiceItem invoiceItem2 = invoiceItemDao.getInvoiceItemById(invoiceItem1.getInvoiceItemId());

        assertEquals(invoiceItem1, invoiceItem2);
    }

    @Test
    public void deleteInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(3);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(invoice.getInvoiceId());
        invoiceItem1.setInventoryId(4);
        invoiceItem1.setQuantity(6);
        invoiceItem1.setUnitPrice(new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP));
        invoiceItem1 = invoiceItemDao.createInvoiceItem(invoiceItem1);

        invoiceItemDao.deleteInvoiceItem(invoiceItem1.getInvoiceItemId());

        invoiceItem1 = invoiceItemDao.getInvoiceItemById(invoiceItem1.getInvoiceItemId());

        assertNull(invoiceItem1);
    }
}