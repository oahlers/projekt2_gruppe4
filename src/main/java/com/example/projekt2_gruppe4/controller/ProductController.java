package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/createProduct")
    public String showCreateProductForm(@RequestParam("wishlistId") int wishlistId, Model model) {
        model.addAttribute("wishlistId", wishlistId);
        return "createProduct";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") double price,
                             @RequestParam("wishlistId") int wishlistId) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        String insertProductQuery = "INSERT INTO products (name, description, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertProductQuery, product.getName(), product.getDescription(), product.getPrice());

        String lastInsertProductIdQuery = "SELECT LAST_INSERT_ID()";
        int productId = jdbcTemplate.queryForObject(lastInsertProductIdQuery, Integer.class);

        String insertWishlistProductQuery = "INSERT INTO wishlist_products (wishlist_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(insertWishlistProductQuery, wishlistId, productId);

        return "redirect:/showWishlist?wishlistId=" + wishlistId;
    }
}

