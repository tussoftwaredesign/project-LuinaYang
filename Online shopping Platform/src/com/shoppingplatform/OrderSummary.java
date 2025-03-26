package com.shoppingplatform;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.time.LocalDateTime;

public record OrderSummary(int orderId, double totalPrice, LocalDateTime orderDate)
 {
     @Override
     public String toString() {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         return "\nOrder ID: " + orderId +
                 ", Total Price: " + totalPrice + "€" +
                 ", Order Date: " + orderDate.format(formatter);
     }

}
