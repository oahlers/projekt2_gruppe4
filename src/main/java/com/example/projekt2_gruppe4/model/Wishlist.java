package com.example.projekt2_gruppe4.model;

import java.util.List;

public class Wishlist {
    private int id;
    private String name;
    private String description;
    private String pincode;
    private int userId;
    private String shareToken;
    private List<Product> products;

    public Wishlist() {}

    public Wishlist(int id, String name, String description, String pincode, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pincode = pincode;
        this.userId = userId;
    }

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

    public String getShareToken() {
        return shareToken;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }
}