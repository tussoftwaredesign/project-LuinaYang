package com.shoppingplatform;

public class Customer extends User {
    private int loyaltyPoints;

    public Customer(int id, String name, String email, String password, int loyaltyPoints) {
        super(id, name, email, password); // Call the superclass (User) constructor
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String toString() {
        return super.toString() + ", Customer{loyaltyPoints=" + loyaltyPoints + "}";
    }

    // Getter and setter for loyaltyPoints
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    // Additional methods specific to customers can go here
}
