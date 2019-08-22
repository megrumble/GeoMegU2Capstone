package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.dto.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);

    public InvoiceItem getInvoiceItemById(int id);

    public List<InvoiceItem> getAllInvoiceItems();

    public List<InvoiceItem> getInvoiceItemByInvoiceId(int id);

    public void updateInvoiceItem(InvoiceItem invoiceItem);

    public void deleteInvoiceItem(int id);

}
