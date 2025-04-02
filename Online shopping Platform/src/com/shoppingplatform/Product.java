package com.shoppingplatform;

public class Product {
    private int id;
    private String name;
    private double price;
    private ProductCategory category;
    private int stockQuantity;

    // Constructor
    public Product(int id, String name, double price, ProductCategory category, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    public synchronized boolean purchase(int quantity) {
        if (stockQuantity >= quantity) {
            stockQuantity -= quantity;
            return true;
        }
        return false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "\nID: " + id +
                ", Name: " + name +
                ", Price: " + price +
                ", Category: " + category +
                ", Stock: " + stockQuantity;

    }
}
