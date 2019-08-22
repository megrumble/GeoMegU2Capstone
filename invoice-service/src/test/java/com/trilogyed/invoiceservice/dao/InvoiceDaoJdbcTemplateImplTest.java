package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {
        invoiceItemDao.getAllInvoiceItems().stream()
                .forEach(invItem -> invoiceItemDao.deleteInvoiceItem(invItem.getInvoiceItemId()));

        invoiceDao.getAllInvoices().stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetInvoice() {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);

        Invoice invoice1 = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, invoice1);

        invoice1 = invoiceDao.getInvoice(2);

        assertNull(invoice1);

    }

    @Test
    public void getAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        List<Invoice> emptyInvoiceList = invoiceDao.getAllInvoices();

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);
        invoiceList.add(invoice);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(2);
        invoice1.setPurchaseDate(LocalDate.now());
        invoice1 = invoiceDao.createInvoice(invoice1);
        invoiceList.add(invoice1);

        List<Invoice> invoiceList2 = invoiceDao.getAllInvoices();

        assertEquals(invoiceList, invoiceList2);
        assertTrue(emptyInvoiceList.isEmpty());
    }

    @Test
    public void getInvoicesByCustomerId() {
        List<Invoice> invoiceList = new ArrayList<>();
        List<Invoice> emptyInvoiceList = invoiceDao.getInvoicesByCustomerId(9);

        Invoice invoice = new Invoice();
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice = invoiceDao.createInvoice(invoice);
        invoiceList.add(invoice);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 03, 05));
        invoice1 = invoiceDao.createInvoice(invoice1);
        invoiceList.add(invoice1);

        List<Invoice> invoicesFromDao = invoiceDao.getInvoicesByCustomerId(1);

        assertEquals(invoiceList, invoicesFromDao);
        assertTrue(emptyInvoiceList.isEmpty());
    }

    @Test
    public void updateInvoice() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 03, 05));
        invoice1 = invoiceDao.createInvoice(invoice1);

        invoice1.setPurchaseDate(LocalDate.now());

        invoiceDao.updateInvoice(invoice1);

        Invoice invoice2 = invoiceDao.getInvoice(invoice1.getInvoiceId());

        assertEquals(invoice1, invoice2);
    }

    @Test
    public void deleteInvoice() {
        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019, 03, 05));
        invoice1 = invoiceDao.createInvoice(invoice1);

        invoiceDao.deleteInvoice(invoice1.getInvoiceId());

        Invoice invoice2 = invoiceDao.getInvoice(invoice1.getInvoiceId());

        assertNull(invoice2);
    }
}