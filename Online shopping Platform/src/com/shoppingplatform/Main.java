package com.shoppingplatform;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        // Initialize data
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 1200.0, ProductCategory.ELECTRONICS, 10));
        products.add(new Product(2, "Jeans", 50.0, ProductCategory.CLOTHING, 5));
        products.add(new Product(3, "Microwave", 200.0, ProductCategory.HOME_APPLIANCES, 3));

        Admin admin = new Admin(1, "Admin", "admin@example.com", "password");
        Customer customer = new Customer(2, "Customer", "customer@example.com", "password");

        Cart cart = new Cart();
        Order order = null;

        String[] userTypes = {"Admin", "Customer", "Exit"};
        boolean exit = false;

        while (!exit) {
            // User type selection
            int userTypeChoice = JOptionPane.showOptionDialog(null, "Select User Type", "Shopping Platform",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, userTypes, userTypes[0]);

            switch (userTypeChoice) {
                case 0 -> {
                    if (authenticateUser(admin)) {
                        handleAdminActions(admin, products);
                    }
                }
                case 1 -> {
                    if (authenticateUser(customer)) {
                        handleCustomerActions(customer, products, cart, order);
                    }
                }
                case 2 -> {
                    exit = true;
                    JOptionPane.showMessageDialog(null, "Thank you for using the shopping platform!");
                }
            }
        }
    }

    // Authenticate user login
    private static boolean authenticateUser(Manageable user) {
        String email = JOptionPane.showInputDialog("Enter your email:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        user.login(email, password);

        switch (user) {
            case Admin adminUser -> {
                if (adminUser.getEmail().equals(email) && adminUser.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful as Admin!");
                    user.viewProfile();
                    return true;
                }
            }
            case Customer customerUser -> {
                if (customerUser.getEmail().equals(email) && customerUser.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, "Login successful as Customer!");
                    user.viewProfile();
                    return true;
                }
            }
            default -> JOptionPane.showMessageDialog(null, "Login failed. Unknown user type.");
        }

        JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
        return false;
    }

    // Handle Admin actions
    private static void handleAdminActions(Admin admin, List<Product> products) {
        String[] adminOptions = {"View Products", "Add Product", "Remove Product", "Logout"};
        boolean logout = false;

        while (!logout) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an Action", "Admin Actions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, adminOptions, adminOptions[0]);

            switch (choice) {
                case 0 -> {
                    StringBuilder productList = new StringBuilder("Product List:\n");
                    for (Product product : products) {
                        productList.append(product.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, productList.toString());
                }
                case 1 -> {
                    try {
                        String idStr = JOptionPane.showInputDialog("Enter Product ID:");
                        String name = JOptionPane.showInputDialog("Enter Product Name:");
                        String priceStr = JOptionPane.showInputDialog("Enter Product Price:");
                        String categoryStr = JOptionPane.showInputDialog("Enter Product Category (ELECTRONICS, CLOTHING, HOME_APPLIANCES, BOOKS, BEAUTY):");
                        String stockStr = JOptionPane.showInputDialog("Enter Stock Quantity:");

                        int id = Integer.parseInt(idStr);
                        double price = Double.parseDouble(priceStr);
                        int stock = Integer.parseInt(stockStr);
                        ProductCategory category = ProductCategory.valueOf(categoryStr.toUpperCase());

                        Product newProduct = new Product(id, name, price, category, stock);
                        admin.addProduct(products, newProduct);
                        JOptionPane.showMessageDialog(null, "Product added successfully:\n" + newProduct.toString());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error adding product: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        String idStr = JOptionPane.showInputDialog("Enter Product ID to Remove:");
                        int productId = Integer.parseInt(idStr);
                        admin.removeProduct(products, productId);
                        JOptionPane.showMessageDialog(null, "Product removed successfully if it existed.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error removing product: " + e.getMessage());
                    }
                }
                case 3 -> {
                    admin.logout();
                    logout = true;
                }
            }
        }
    }

    // Handle Customer actions
    private static void handleCustomerActions(Customer customer, List<Product> products, Cart cart, Order order) {
        String[] customerOptions = {"View Products", "Add to Cart", "View Cart", "Checkout", "View Order History", "Logout"};
        boolean logout = false;

        while (!logout) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an Action", "Customer Actions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, customerOptions, customerOptions[0]);

            switch (choice) {
                case 0 -> {
                    StringBuilder productList = new StringBuilder("Product List:\n");
                    for (Product product : products) {
                        productList.append(product.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, productList.toString());
                }
                case 1 -> {
                    try {
                        String idStr = JOptionPane.showInputDialog("Enter Product ID to Add to Cart:");
                        int productId = Integer.parseInt(idStr);
                        Product selectedProduct = products.stream()
                                .filter(p -> p.getId() == productId)
                                .findFirst()
                                .orElse(null);
                        if (selectedProduct != null) {
                            cart.addProduct(selectedProduct);
                            JOptionPane.showMessageDialog(null, "Product added to cart: " + selectedProduct.getName());
                        } else {
                            JOptionPane.showMessageDialog(null, "Product not found.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error adding product to cart: " + e.getMessage());
                    }
                }
                case 2 -> {
                    StringBuilder cartContents = new StringBuilder("Cart Contents:\n");
                    cart.displayCart();
                    JOptionPane.showMessageDialog(null, cartContents.toString());
                }
                case 3 -> {
                    try {
                        order = new Order(1, cart);
                        customer.addOrderToHistory(order);
                        JOptionPane.showMessageDialog(null, "Order placed successfully!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error during checkout: " + e.getMessage());
                    }
                }
                case 4 -> customer.viewOrderHistory();
                case 5 -> {
                    customer.logout();
                    logout = true;
                }
            }
        }
    }
}
