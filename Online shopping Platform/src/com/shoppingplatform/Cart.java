package com.shoppingplatform;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    // Constructor
    public Cart() {
        this.products = new ArrayList<>();
    }

    // Add product to cart
    public void addProduct(Product product) {
        products.add(product);
        System.out.println(product.getName() + " added to the cart.");
    }

    // Remove product from cart
    public void removeProduct(Product product) {
        products.remove(product);
        System.out.println(product.getName() + " removed from the cart.");
    }

    // Calculate total price
    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    // Display cart contents
    public void displayCart() {
        System.out.println("Cart contents:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println("Total price: $" + calculateTotal());
    }
}
