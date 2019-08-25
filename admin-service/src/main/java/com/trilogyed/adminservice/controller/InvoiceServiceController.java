package com.trilogyed.adminservice.controller;

import com.trilogyed.adminservice.exception.NotFoundException;
import com.trilogyed.adminservice.model.InvoiceViewModel;
import com.trilogyed.adminservice.service.AdminServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RefreshScope
public class InvoiceServiceController {

    @Autowired
    AdminServiceLayer service;

    public InvoiceServiceController(AdminServiceLayer service) {
        this.service = service;
    }

    @RequestMapping(value = "/invoice/invoice", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@Valid @RequestBody InvoiceViewModel ivm, Principal principal) {

        return service.addInvoice(ivm);
    }

    @RequestMapping(value = "/invoice/invoice/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") int id, Principal principal) {
        InvoiceViewModel ivm = service.findInvoice(id);
        if (ivm == null){
            throw new NotFoundException("No invoice found for id " + id);
        }
        return ivm;
    }

    @RequestMapping(value = "/invoice/invoice", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(Principal principal) {
        List<InvoiceViewModel> ivmList = service.findAllInvoices();
        if (ivmList.isEmpty()){
            throw new NotFoundException("No invoices found");
        }
        return ivmList;
    }

    @RequestMapping(value = "/invoice/invoice/customer/{custId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable("custId") int id, Principal principal) {
        List<InvoiceViewModel> ivmList = service.findInvoicesByCustomer(id);
        if (ivmList.isEmpty()) {
            throw new NotFoundException("No invoices found for customer id " + id);
        }
        return ivmList;
    }

    @RequestMapping(value = "/invoice/invoice", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@Valid @RequestBody InvoiceViewModel ivm, Principal principal) {
        service.updateInvoice(ivm);
    }

    @RequestMapping(value = "/invoice/invoice/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int id, Principal principal) {
        service.deleteInvoice(id);
    }
}
