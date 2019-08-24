package com.trilogyed.retailapiservice.models;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {
    private int productId;
    private int quantity;
    private BigDecimal listPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return productId == orderItem.productId &&
                quantity == orderItem.quantity &&
                listPrice.equals(orderItem.listPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity, listPrice);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", listPrice=" + listPrice +
                '}';
    }
}
