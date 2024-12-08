package com.shoppingplatform;

import java.util.Date;

public record OrderSummary(int orderId, double totalPrice, Date orderDate) {
    @Override
    public String toString() {
        return "\nOrder ID: " + orderId +
                ", Total Price:" + totalPrice +
                "€" +
                ", Order Date:" + orderDate;
    }
}
