package com.shoppingplatform;

import java.util.Arrays;
import java.util.Date;

public class Order {
    private int orderId;
    private Product[] orderItems; // Array to store order items
    private Date orderDate;
    private OrderStatus status;

    // Constructor that takes Cart and converts it to an array of order items
    public Order(int orderId, Cart cart) {
        this.orderId = orderId;
        this.orderItems = new Product[cart.getProducts().size()];
        cart.getProducts().toArray(orderItems); // Convert List to array
        this.orderDate = new Date(); // Set the order date to current date
        this.status = OrderStatus.PENDING;
        cart.clearCart(); // Clear the cart after converting to order
    }

    // Defensive Copy for getOrderItems
    public Product[] getOrderItems() {
        return Arrays.copyOf(orderItems, orderItems.length); // Return a copy of the array
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
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
        System.out.println("Order Items:");
        for (Product item : orderItems) {
            System.out.println(item);
        }
    }
}
