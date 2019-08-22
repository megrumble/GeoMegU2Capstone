package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.Invoice;

import java.util.List;

public interface InvoiceDao {

    public Invoice createInvoice(Invoice invoice);

    public Invoice getInvoice(int id);

    public List<Invoice> getAllInvoices();

    public List<Invoice> getInvoicesByCustomerId(int id);

    public void updateInvoice(Invoice invoice);

    public void deleteInvoice(int id);
}
