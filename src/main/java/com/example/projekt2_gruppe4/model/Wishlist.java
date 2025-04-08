package com.example.projekt2_gruppe4.model;

import java.util.List;

public class Wishlist {
    private int id;
    private String title;
    private String description;
    private String pincode;
    private int userId;
    private List<Product> products;
    private String shareToken; // 👈 Tilføj denne linje

    // Standard konstruktor
    public Wishlist() {}

    // Konstruktor med parametre
    public Wishlist(int id, String title, String description, String pincode, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pincode = pincode;
        this.userId = userId;
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }
}