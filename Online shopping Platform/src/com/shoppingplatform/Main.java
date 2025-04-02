package com.shoppingplatform;

import java.util.ResourceBundle;
import java.util.Locale;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    private static ResourceBundle bundle;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH); // or Locale.CHINESE
        bundle = ResourceBundle.getBundle(
                "messages",
                Locale.getDefault(),
                ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES)
        );

        // Initialize data
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "MacBook Pro 16", 1200.0, ProductCategory.LAPTOPS, 10));
        products.add(new Product(2, "Dell XPS", 900.0, ProductCategory.COMPUTERS, 5));
        products.add(new Product(3, "Samsung Galaxy Tab S8", 600.0, ProductCategory.SMARTPHONES, 10));
        products.add(new Product(4, "Redmi K70", 300.0, ProductCategory.SMARTPHONES, 0));

        Admin admin = new Admin(1, "Admin", "user1@shop.com", "123");
        Customer customer = new Customer(2, "Customer", "user2@shop.com", "321");

        Cart cart = new Cart();

        String[] userTypes = {
                bundle.getString("button.admin"),
                bundle.getString("button.customer"),
                bundle.getString("button.exit")
        };

        boolean exit = false;

        while (!exit) {
            // User type selection
            int userTypeChoice = JOptionPane.showOptionDialog(null,
                    bundle.getString("user.select.type"),
                    bundle.getString("platform.title"),
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
                    JOptionPane.showMessageDialog(null, bundle.getString("thank.you"));
                }
            }
        }
    }

    // Authenticate user login
    private static boolean authenticateUser(Manageable user) {
        String email = JOptionPane.showInputDialog(bundle.getString("input.enter.email"));
        String password = JOptionPane.showInputDialog(bundle.getString("input.enter.password"));
        user.login(email, password);

        switch (user) {
            case Admin adminUser -> {
                if (adminUser.getEmail().equals(email) && adminUser.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, bundle.getString("login.success.admin"));
                    user.viewProfile();
                    return true;
                }
            }
            case Customer customerUser -> {
                if (customerUser.getEmail().equals(email) && customerUser.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(null, bundle.getString("login.success.customer"));
                    user.viewProfile();
                    return true;
                }
            }
            default -> JOptionPane.showMessageDialog(null, "Login failed. Unknown user type.");
        }

        JOptionPane.showMessageDialog(null, bundle.getString("login.failed"));
        return false;
    }

    // Handle Admin actions
    private static void handleAdminActions(Admin admin, List<Product> products) {
        String[] adminOptions = {
                bundle.getString("menu.admin.view.products"),
                bundle.getString("menu.admin.add.product"),
                bundle.getString("menu.admin.remove.product"),
                bundle.getString("menu.admin.view.statistics"),
                bundle.getString("menu.admin.check.inventory"),
                bundle.getString("menu.admin.export.file"),
                bundle.getString("menu.admin.logout")
        };

        boolean logout = false;

        while (!logout) {
            int choice = JOptionPane.showOptionDialog(null, bundle.getString("menu.choose.action"), "Admin Actions",
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
                    boolean allInStock = products.stream()
                            .allMatch(product -> product.getStockQuantity() > 0);

                    if (allInStock) {
                        JOptionPane.showMessageDialog(null, "✅ All products are currently in stock.");
                    } else {
                        // List out of stock items
                        StringBuilder outOfStockList = new StringBuilder("❌ Some products are out of stock:\n\n");
                        products.stream()
                                .filter(p -> p.getStockQuantity() <= 0)
                                .forEach(p -> outOfStockList.append(p.toString()).append("\n"));
                        JOptionPane.showMessageDialog(null, outOfStockList.toString());
                    }
                }

                case 5 -> {
                    try {
                        List<String> productLines = products.stream()
                                .map(Product::toString)
                                .collect(Collectors.toList());

                        Path filePath = Paths.get("products.txt");
                        Files.write(filePath, productLines, StandardCharsets.UTF_8);

                        JOptionPane.showMessageDialog(null,
                                "✅ Product list exported successfully to 'products.txt'");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                                "❌ Failed to export product list: " + e.getMessage());
                    }
                }

                case 6 -> {
                    admin.logout();
                    logout = true;
                }
            }
        }
    }

    // Handle Customer actions
    private static void handleCustomerActions(Customer customer, List<Product> products, Cart cart) {
        String[] customerOptions = {
                bundle.getString("menu.customer.view.products"),
                bundle.getString("menu.customer.add.to.cart"),
                bundle.getString("menu.customer.view.cart"),
                bundle.getString("menu.customer.checkout"),
                bundle.getString("menu.customer.order.history"),
                bundle.getString("menu.customer.logout")
        };

        boolean logout = false;

        while (!logout) {
            int choice = JOptionPane.showOptionDialog(null, bundle.getString("menu.choose.action"), "Customer Actions",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, customerOptions, customerOptions[0]);

            switch (choice) {
                case 0 -> {
                    boolean back = false;
                    while (!back) {
                        String[] viewOptions = {
                                "Sorted by Price",
                                "Sorted by Category",
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

                        Consumer<Product> displayProduct = product -> productList.append(product.toString()).append("\n");

                        switch (viewChoice) {
                            case 0 -> {
                                productList.append("Product List (Sorted by Price Ascending):\n");
                                products.stream()
                                        .sorted(Comparator.comparing(Product::getPrice))
                                        .forEach(displayProduct);
                                JOptionPane.showMessageDialog(null, productList.toString());
                            }
                            case 1 -> { // Group by Category
                                Map<ProductCategory, List<Product>> grouped = products.stream()
                                        .collect(Collectors.groupingBy(Product::getCategory));

                                productList.append("Products Grouped by Category:\n\n");

                                grouped.forEach((category, productGroup) -> {
                                    productList.append("Category: ").append(category).append("\n");
                                    productGroup.forEach(p -> productList.append("  - ")
                                            .append(p.getName())
                                            .append(" (€")
                                            .append(p.getPrice())
                                            .append(")\n"));
                                    productList.append("\n");
                                });

                                JOptionPane.showMessageDialog(null, productList.toString());
                            }

                            case 2 -> {
                                productList.append("In-Stock Products:\n");
                                Predicate<Product> inStock = p -> p.getStockQuantity() > 0;
                                products.stream()
                                        .filter(inStock)
                                        .forEach(displayProduct);
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
