package com.shoppingplatform;

// Checked exception
public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
