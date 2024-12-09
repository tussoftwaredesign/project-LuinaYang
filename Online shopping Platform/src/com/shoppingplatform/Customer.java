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

    public List<OrderSummary> getOrderHistory() {
        return orderHistory;
    }

    // Local Variable Type Inference(LVTI)
    public void addOrderToHistory(Order order) {
        var totalPrice = 0.0;
        for (Product item : order.getOrderItems()) {
            totalPrice += item.getPrice();
        }
        orderHistory.add(new OrderSummary(order.getOrderId(), totalPrice, order.getOrderDate()));
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: Customer";
    }

}
