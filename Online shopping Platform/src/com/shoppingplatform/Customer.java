package com.shoppingplatform;

public class Customer extends User {

    public Customer(int id, String name, String email, String password) {
        super(id, name, email, password); // Call the superclass (User) constructor
    }

    @Override
    public String toString() {
        return super.toString();
    }

    // Additional methods specific to customers can go here
}