package com.shoppingplatform;

public class Customer extends User implements Manageable {
    public Customer(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    // Implement login method
    @Override
    public void login(String email, String password) {
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
}
