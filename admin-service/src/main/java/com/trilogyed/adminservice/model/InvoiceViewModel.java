package com.trilogyed.adminservice.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {
    private int invoiceId;
    @Min(value = 1, message = "Invalid customer id, must be int greater than zero")
    private int customerId;
    @NotNull(message = "Invalid date, cannot be null")
    private LocalDate purchaseDate;
    @Valid
    private List<InvoiceItem> invoiceItemsList;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItem> getInvoiceItemsList() {
        return invoiceItemsList;
    }

    public void setInvoiceItemsList(List<InvoiceItem> invoiceItemsList) {
        this.invoiceItemsList = invoiceItemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        boolean check = false;
        if(getInvoiceId() == that.getInvoiceId()) {
            check = true;
        }else{
            return false;
        }
        if(getCustomerId() == that.getCustomerId()){
            check=true;
        }else{
            return false;
        }
        if((getPurchaseDate() == null && that.getPurchaseDate() == null) || getPurchaseDate().equals(that.getPurchaseDate())) {
            check = true;
        }else{
            return false;
        }
        if((getInvoiceItemsList() == null && that.getInvoiceItemsList() == null) ||
                (getInvoiceItemsList().containsAll(that.getInvoiceItemsList()) && that.getInvoiceItemsList().containsAll(getInvoiceItemsList()))){
            check = true;
        }else{
            return false;
        }
        return check ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemsList);
    }

    @Override
    public String toString() {
        return "InvoiceViewModel{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", purchaseDate=" + purchaseDate +
                ", invoiceItemsList=" + invoiceItemsList +
                '}';
    }
}
