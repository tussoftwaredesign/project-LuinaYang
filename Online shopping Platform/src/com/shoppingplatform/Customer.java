package com.shoppingplatform;

import java.util.ArrayList;
import java.util.List;

public final class Customer extends User implements Manageable {
    private final List<OrderSummary> orderHistory = new ArrayList<>();
    public Customer(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    // Implement login method
    @Override
    public void login(String email, String password) {
        if (!validateEmail(email, password)) {
            System.out.println("Login failed due to invalid email format.");
            return;
        }
        if (this.getEmail().equals(email) && this.getPassword().equals(password)) {
            System.out.println("Customer logged in successfully.");
        } else {
            System.out.println("Customer login failed.");
        }
    }

    // Implement viewProfile method
    @Override
    public void viewProfile() {
        System.out.println("Customer Profile: " + this.toString());
    }

    public void addOrderToHistory(Order order) {
        double totalPrice = 0.0;
        for (Product item : order.getOrderItems()) {
            totalPrice += item.getPrice();
        }
        orderHistory.add(new OrderSummary(order.getOrderId(), totalPrice, order.getOrderDate()));
    }

    public void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("Order History:");
            for (OrderSummary summary : orderHistory) {
                System.out.println(summary);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: Customer";
    }

}
