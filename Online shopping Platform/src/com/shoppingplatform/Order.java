package com.shoppingplatform;

import java.util.Date;

public class Order {
    private int orderId;
    private Cart cart;
    private Date orderDate;
    private OrderStatus status;

    // Constructor
    public Order(int orderId, Cart cart) {
        this.orderId = orderId;
        this.cart = cart;
        this.orderDate = new Date(); // Set the order date to current date
        this.status = OrderStatus.PENDING;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public Cart getCart() {
        return cart;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Display order details
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Order Status: " + status);
        cart.displayCart();
    }
}
