package com.shoppingplatform;

import java.util.List;

public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password); // Call the superclass (User) constructor
    }

    // Add a product to the product list
    public void addProduct(List<Product> products, Product product) {
        products.add(product);
        System.out.println(product.getName() + " has been added to the product list.");
    }

    // Remove a product by ID
    public void removeProduct(List<Product> products, int productId) {
        Product productToRemove = null;
        for (Product product : products) {
            if (product.getId() == productId) {
                productToRemove = product;
                break;
            }
        }
        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println(productToRemove.getName() + " has been removed from the product list.");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Admin{role='Administrator'}";
    }
}
