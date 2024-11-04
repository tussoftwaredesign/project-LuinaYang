package com.shoppingplatform;

public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password); // Call the superclass (User) constructor
    }

    // Admin-specific methods can be added here
    public void manageProduct() {
        System.out.println("Managing products...");
    }
}
