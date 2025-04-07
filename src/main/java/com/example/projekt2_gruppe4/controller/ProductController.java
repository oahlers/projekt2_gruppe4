package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/add")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") double price,
                             @RequestParam("wishlistId") int wishlistId) {
        // Opret produktobjekt og sæt værdier
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setWishlistId(wishlistId);  // Metoden tilføjet i Product-klassen

        // Gem produktet ved hjælp af JdbcTemplate
        String insertQuery = "INSERT INTO product (name, description, price, wishlist_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, product.getName(), product.getDescription(), product.getPrice(), product.getWishlistId());

        return "redirect:/products"; // Redirect til produktlisten
    }
}