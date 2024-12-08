package com.shoppingplatform;

// Sealed interface Manageable
public sealed interface Manageable permits Admin, Customer {
    void login(String email, String password);
    void viewProfile();

    // Default method for logout
    default void logout() {
        System.out.println("User logged out successfully.");
    }

    // Private method to validate email format
    private boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Default method using private validation logic
    default boolean validateEmail(String email, String password) {
        if (!isEmailValid(email)) {
            System.out.println("Invalid email format.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }
        return true;
    }

}
