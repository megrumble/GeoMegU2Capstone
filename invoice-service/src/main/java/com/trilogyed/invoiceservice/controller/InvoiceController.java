package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.service.InvoiceService;
import com.trilogyed.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@Valid @RequestBody InvoiceViewModel ivm) {

        return invoiceService.addInvoice(ivm);
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") int id) {
        InvoiceViewModel ivm = invoiceService.findInvoice(id);
        if (ivm == null){
            throw new NotFoundException("No invoice found for id " + id);
        }
        return ivm;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> ivmList = invoiceService.findAllInvoices();
        if (ivmList.isEmpty()){
            throw new NotFoundException("No invoices found");
        }
        return ivmList;
    }

    @RequestMapping(value = "/invoice/customer/{custId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable("custId") int id) {
        List<InvoiceViewModel> ivmList = invoiceService.findInvoiceByCustomerId(id);
        if (ivmList.isEmpty()) {
            throw new NotFoundException("No invoices found for customer id " + id);
        }
        return ivmList;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@Valid @RequestBody InvoiceViewModel ivm) {
        invoiceService.updateInvoice(ivm);
    }

    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int id) {
        invoiceService.deleteInvoice(id);
    }
}
