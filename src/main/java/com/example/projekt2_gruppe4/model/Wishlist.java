package com.example.projekt2_gruppe4.model;

import java.util.List;

public class Wishlist {
    private int id;
    private int userId;
    private String name;
    private List<Product> products; // Liste af produkter i ønskelisten

    public Wishlist() {}

    public Wishlist(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Wishlist(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    // Getters og setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}