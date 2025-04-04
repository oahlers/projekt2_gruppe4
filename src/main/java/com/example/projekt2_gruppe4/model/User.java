package com.example.projekt2_gruppe4.model;

public class User {
    private int id;
    private String username;
    private String password;

    // Default konstruktør (nødvendig for RowMapper)
    public User() {
    }

    // Konstruktør med username og password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Konstruktør med alle felter (valgfri)
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}