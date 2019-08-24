package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.viewmodel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/invoice")
@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {

    @PostMapping
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel ivm);

    @GetMapping("/{id}")
    public InvoiceViewModel getInvoice(@PathVariable int id);

    @GetMapping
    public List<InvoiceViewModel> getAllInvoices();

    @GetMapping("customer/{custId}")
    public List<InvoiceViewModel> getInvoicesByCustomer(@PathVariable("custId") int id);

    @PutMapping
    public void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm);

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable int id);


}