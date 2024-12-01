package com.shoppingplatform;

import java.util.List;

public class Admin extends User implements Manageable {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    // Implement login method
    @Override
    public void login(String email, String password) {
        if (this.getEmail().equals(email) && this.getPassword().equals(password)) {
            System.out.println("Admin logged in successfully.");
        } else {
            System.out.println("Admin login failed.");
        }
    }

    // Implement viewProfile method
    @Override
    public void viewProfile() {
        System.out.println("Admin Profile: " + this.toString());
    }

    // Admin-specific methods
    public void addProduct(List<Product> products, Product product) {
        products.add(product);
        System.out.println(product.getName() + " has been added to the product list.");
    }

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
}
