package com.trilogyed.retailapiservice.viewmodel;

import com.trilogyed.retailapiservice.models.InvoiceItem;
import com.trilogyed.retailapiservice.models.OrderItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RetailViewModel {
    private int invoiceId;
    @Min(value = 1, message = "Customer ID cannot be zero")
    private int customerId;
    @NotNull(message = "No products in order")
//    private List<InvoiceItem> invoiceItems;
    private List<OrderItem> orderItems;
    private LocalDate purchaseDate;
    private int points;

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailViewModel that = (RetailViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                points == that.points &&
                orderItems.equals(that.orderItems) &&
                purchaseDate.equals(that.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, orderItems, purchaseDate, points);
    }

    @Override
    public String toString() {
        return "RetailViewModel{" +
                "invoiceId=" + invoiceId +
                ", customerId=" + customerId +
                ", orderItems=" + orderItems +
                ", purchaseDate=" + purchaseDate +
                ", points=" + points +
                '}';
    }
}
