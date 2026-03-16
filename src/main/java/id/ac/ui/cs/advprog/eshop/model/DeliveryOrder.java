package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class DeliveryOrder {
    private String id;
    private String destination;
    private double weight;
    private String type; // "STANDARD", "EXPRESS", "DRONE"
    private String status; // "PENDING", "ASSIGNED", "IN_TRANSIT", "DELIVERED"
    private List<String> history = new ArrayList<>();
    private String anotherAttribute;

    // Imagine 20 more fields here: recipientName, phoneNumber, dimensions, etc.
    // Standard Getters/Setters...
}