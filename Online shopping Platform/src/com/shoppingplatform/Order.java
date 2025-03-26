package com.shoppingplatform;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public final class Order {
    private final int orderId;
    private static int lastOrderId = 0; // Static variable to track the last order ID
    private final Product[] orderItems; // Array to store order items
    private final LocalDateTime orderDate;
//    private final OrderStatus status;

    // Constructor
    public Order(Cart cart) {
        this.orderId = ++lastOrderId; // Auto-increment the Order ID
        this.orderItems = Arrays.copyOf(cart.getProducts().toArray(new Product[0]), cart.getProducts().size()); // Defensive copy
        this.orderDate = LocalDateTime.now(); // Use local time API
//        this.status = OrderStatus.PENDING;
        cart.clearCart(); // Clear the cart after converting to order
    }

    // Getters with defensive copying for array
    public int getOrderId() {
        return orderId;
    }

    public Product[] getOrderItems() {
        return Arrays.copyOf(orderItems, orderItems.length); // Return a copy of the array
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


//    public OrderStatus getStatus() {
//        return status;
//    }

    // No setters to maintain immutability
}
