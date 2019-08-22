package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.dto.Invoice;
import com.trilogyed.invoiceservice.dto.InvoiceItem;
import com.trilogyed.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceService {

    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceItemDao invoiceItemDao;

    public InvoiceService(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {
        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    public InvoiceViewModel addInvoice(InvoiceViewModel ivm) {
        Invoice invoice = new Invoice();
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());
        invoice = invoiceDao.createInvoice(invoice);

        ivm.setInvoiceId(invoice.getInvoiceId());

//        List<InvoiceItem> invoiceItemList = ivm.getInvoiceItemsList();

        ivm.getInvoiceItemsList().stream()
                .forEach(invItem -> invItem.setInvoiceId(ivm.getInvoiceId()));

        ivm.getInvoiceItemsList().stream()
                .forEach(invItem -> invItem = invoiceItemDao.createInvoiceItem(invItem));

        return ivm;
    }

    public InvoiceViewModel findInvoice(int id) {
        return
                buildInvoiceViewModel(invoiceDao.getInvoice(id));
    }

    public List<InvoiceViewModel> findAllInvoices() {
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for (Invoice inv : invoiceList) {
            ivmList.add(buildInvoiceViewModel(inv));
        }

        return ivmList;
    }

    public List<InvoiceViewModel> findInvoiceByCustomerId(int id) {
        List<Invoice> invoiceList = invoiceDao.getInvoicesByCustomerId(id);
        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for (Invoice inv : invoiceList) {
            ivmList.add(buildInvoiceViewModel(inv));
        }

        return ivmList;
    }

    public void updateInvoice(InvoiceViewModel ivm) {

        invoiceItemDao.getInvoiceItemByInvoiceId(ivm.getInvoiceId())
                .stream()
                .forEach(invItem -> invoiceItemDao.deleteInvoiceItem(invItem.getInvoiceItemId()));

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(ivm.getInvoiceId());
        invoice.setCustomerId(ivm.getCustomerId());
        invoice.setPurchaseDate(ivm.getPurchaseDate());

        invoiceDao.updateInvoice(invoice);

        ivm.getInvoiceItemsList()
                .stream()
                .forEach(invItem -> invItem.setInvoiceId(ivm.getInvoiceId()));

        ivm.getInvoiceItemsList()
                .stream()
                .forEach(invItem -> invoiceItemDao.createInvoiceItem(invItem));

    }

    public void deleteInvoice(int id) {
        invoiceItemDao.getInvoiceItemByInvoiceId(id)
                .stream()
                .forEach(invItem -> invoiceItemDao.deleteInvoiceItem(invItem.getInvoiceItemId())
                );

        invoiceDao.deleteInvoice(id);

    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setInvoiceItemsList(invoiceItemDao.getInvoiceItemByInvoiceId(invoice.getInvoiceId()));

        return ivm;
    }
}
