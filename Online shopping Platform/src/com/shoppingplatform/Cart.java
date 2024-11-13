package com.shoppingplatform;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    // Constructor
    public Cart() {
        this.products = new ArrayList<>();
    }

//    // Original method to add a single product
//    public void addProduct(Product product) {
//        products.add(product);
//        System.out.println(product.getName() + " added to the cart.");
//    }

    // Add product to cart with Checked Exception
    public void addProduct(Product product) throws OutOfStockException {
        if (product.getStockQuantity() <= 0) {
            throw new OutOfStockException("Product " + product.getName() + " is out of stock.");
        }
        products.add(product);
        System.out.println(product.getName() + " added to the cart.");
    }

    // Overloaded method to add multiple products using varargs
    public void addProduct(Product... productsToAdd) {
        for (Product product : productsToAdd) {
            products.add(product);
            System.out.println(product.getName() + " added to the cart.");
        }
    }

    // Overloaded method to add a list of products
    public void addProduct(List<Product> productList) {
        products.addAll(productList);
        for (Product product : productList) {
            System.out.println(product.getName() + " added to the cart.");
        }
    }

//    // Calculate total price
//    public double calculateTotal() {
//        double total = 0;
//        for (Product product : products) {
//            total += product.getPrice();
//        }
//        return total;
//    }

    // Local Variable Type Inference(LVTI)
    public double calculateTotal() {
        var total = 0.0; // Using 'var' for type inference
        for (var product : products) { // Using 'var' for loop variable
            total += product.getPrice();
        }
        return total;
    }

    // checkout method with Unchecked Exception
    public void checkout() {
        if (products.isEmpty()) {
            throw new EmptyCartException("Cannot checkout because the cart is empty.");
        }
        System.out.println("Checkout successful. Proceeding to payment...");
        // Additional checkout logic here
    }

    // Get products (used to create an order)
    public List<Product> getProducts() {
        return products;
    }

    // Clear cart (used after creating an order)
    public void clearCart() {
        products.clear();
        System.out.println("Cart has been cleared.");
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
