package com.trilogyed.adminservice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceItem {
    private int invoiceItemId;
    private int invoiceId;
    @Min(value = 1, message = "Invalid inventory id, must be int greater than zero")
    private int inventoryId;
    @Min(value = 1, message = "Quantity must be greater than zero")
    private int quantity;
    @NotNull(message = "Invalid unit price, cannot be null")
    private BigDecimal unitPrice;

    public int getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(int invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItem that = (InvoiceItem) o;
        boolean check = false;
        if(getInvoiceId() == that.getInvoiceId())  {
            check = true;
        }else{
            return false;
        }
        if(getInventoryId() ==  that.getInventoryId()){
            check=true;
        }else{
            return false;
        }
        if(getQuantity() == that.getQuantity()){
            check = true;
        }else{
            return false;
        }
        if((getUnitPrice() == null && that.getUnitPrice() == null) ||getUnitPrice().equals(that.getUnitPrice())){
            check = true;
        }else{
            return false;
        }
        return check ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceItemId, invoiceId, inventoryId, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceItemId=" + invoiceItemId +
                ", invoiceId=" + invoiceId +
                ", inventoryId=" + inventoryId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
