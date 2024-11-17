package com.shoppingplatform;

public class User {
    // Private fields for encapsulation
    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Overloaded constructor using this()
    public User(int id, String name, String email) {
        this(id, name, email, "default_password"); // Call the main constructor
    }

    // Override the toString method to provide a basic user info
    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Additional methods can be added here as needed
}
