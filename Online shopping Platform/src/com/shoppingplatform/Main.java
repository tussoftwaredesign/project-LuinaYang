package com.shoppingplatform;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        // Initialize data
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "MacBook Pro 16", 1200.0, ProductCategory.LAPTOPS, 10));
        products.add(new Product(2, "Dell XPS", 900.0, ProductCategory.COMPUTERS, 5));
        products.add(new Product(3, "Samsung Galaxy Tab S8", 600.0, ProductCategory.SMARTPHONES, 10));
        products.add(new Product(4, "Redmi K70", 300.0, ProductCategory.SMARTPHONES, 0));

        Admin admin = new Admin(1, "Admin", "user1@shop.com", "123");
        Customer customer = new Customer(2, "Customer", "user2@shop.com", "321");

        Cart cart = new Cart();

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
                        handleCustomerActions(customer, products, cart);
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
        String[] adminOptions = {
                "View Products",
                "Add Product",
                "Remove Product",
                "View Product Statistics", // new
                "Logout"
        };

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
                        String categoryStr = JOptionPane.showInputDialog("Enter Product Category (LAPTOPS, COMPUTERS, SMARTPHONES,CAMERAS):");
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
                        JOptionPane.showMessageDialog(null, "Product removed successfully.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error removing product: " + e.getMessage());
                    }
                }

                case 3 -> {
                    long totalProducts = products.stream().count();

                    long uniqueCategories = products.stream()
                            .map(Product::getCategory)
                            .distinct()
                            .count();

                    int totalStock = products.stream()
                            .mapToInt(Product::getStockQuantity)
                            .sum();

                    String summary = "Product Statistics:\n\n" +
                            "Total Products: " + totalProducts + "\n" +
                            "Unique Categories: " + uniqueCategories + "\n" +
                            "Total Stock (All Products): " + totalStock;

                    JOptionPane.showMessageDialog(null, summary);
                }

                case 4 -> {
                    admin.logout();
                    logout = true;
                }
            }
        }
    }

    // Handle Customer actions
    private static void handleCustomerActions(Customer customer, List<Product> products, Cart cart) {
        String[] customerOptions = {"View Products", "Add to Cart", "View Cart", "Checkout", "View Order History", "Logout"};
        boolean logout = false;

        while (!logout) {
            int choice = JOptionPane.showOptionDialog(null, "Choose an Action", "Customer Actions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, customerOptions, customerOptions[0]);

            switch (choice) {
                case 0 -> {
                    boolean back = false;
                    while (!back) {
                        String[] viewOptions = {
                                "Sorted by Price",
                                "Original Order",
                                "In-Stock Products",
                                "Back"
                        };

                        int viewChoice = JOptionPane.showOptionDialog(
                                null,
                                "Choose how to view the products:",
                                "View Products",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                viewOptions,
                                viewOptions[0]
                        );

                        StringBuilder productList = new StringBuilder();

                        switch (viewChoice) {
                            case 0 -> {
                                productList.append("Product List (Sorted by Price Ascending):\n");
                                products.stream()
                                        .sorted(Comparator.comparing(Product::getPrice))
                                        .forEach(product -> productList.append(product.toString()).append("\n"));
                                JOptionPane.showMessageDialog(null, productList.toString());
                            }
                            case 1 -> {
                                productList.append("Product List (Original Order):\n");
                                products.forEach(product -> productList.append(product.toString()).append("\n"));
                                JOptionPane.showMessageDialog(null, productList.toString());
                            }

                            case 2 -> {
                                productList.append("In-Stock Products:\n");
                                Predicate<Product> inStock = p -> p.getStockQuantity() > 0;

                                products.stream()
                                        .filter(inStock)
                                        .forEach(product -> productList.append(product.toString()).append("\n"));

                                JOptionPane.showMessageDialog(null, productList.toString());
                            }

                            case 3, JOptionPane.CLOSED_OPTION -> {
                                back = true; // Exit to main customer menu
                            }
                        }
                    }
                }


                case 1 -> {
                    try {
                        String idStr = JOptionPane.showInputDialog("Enter Product ID to Add to Cart:");
                        int productId = Integer.parseInt(idStr);
                        Predicate<Product> isProductAvailable = p -> p.getId() == productId && p.getStockQuantity() > 0;
                        Product selectedProduct = products.stream()
                                .filter(isProductAvailable)
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
                    cart.getProducts().forEach(cartContents::append); //
                    JOptionPane.showMessageDialog(null, cartContents.toString());
                }
                case 3 -> {
                    try {
                        Order order = new Order(cart);
                        customer.addOrderToHistory(order);
                        JOptionPane.showMessageDialog(null, "Order placed successfully!\nOrder ID: " + order.getOrderId());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error during checkout: " + e.getMessage());
                    }
                }
                case 4 -> {
                    if (customer.getOrderHistory().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No orders found.");
                    } else {
                        StringBuilder orderHistory = new StringBuilder("Order History:\n");
                        for (OrderSummary summary : customer.getOrderHistory()) {
                            orderHistory.append(summary.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, orderHistory.toString());
                    }
                }

                case 5 -> {
                    customer.logout();
                    logout = true;
                }
            }
        }
    }
}
