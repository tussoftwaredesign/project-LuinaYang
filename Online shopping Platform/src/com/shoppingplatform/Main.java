package com.shoppingplatform;

public class Main {
    public static void main(String[] args) {
        // Create a Customer
        Customer customer = new Customer(1, "Alice", "alice@example.com", "password123", 100);
        System.out.println("Customer: " + customer.getName() + ", Loyalty Points: " + customer.getLoyaltyPoints());

        // Create an Admin
        Admin admin = new Admin(2, "Bob", "bob@example.com", "adminPass");
        System.out.println("Admin: " + admin.getName());
        admin.manageProduct();
    }
}
