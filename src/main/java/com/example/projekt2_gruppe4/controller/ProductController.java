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

    // GET-metode for at vise formularen til at tilføje et produkt
    @GetMapping("/createProduct")
    public String showCreateProductForm(@RequestParam("wishlistId") int wishlistId, Model model) {
        // Tilføj wishlistId til modellen for at bruge det i formularen
        model.addAttribute("wishlistId", wishlistId);
        return "createProduct"; // Dette svarer til Thymeleaf-templaten 'createProduct.html'
    }

    // POST-metode for at tilføje et produkt
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

        // Gem produktet i 'products'-tabellen
        String insertProductQuery = "INSERT INTO products (name, description, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertProductQuery, product.getName(), product.getDescription(), product.getPrice());

        // Hent ID'et på det nyligt oprettede produkt
        String lastInsertProductIdQuery = "SELECT LAST_INSERT_ID()";
        int productId = jdbcTemplate.queryForObject(lastInsertProductIdQuery, Integer.class);

        // Opret en post i 'wishlist_products' for at knytte produktet til ønskelisten
        String insertWishlistProductQuery = "INSERT INTO wishlist_products (wishlist_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(insertWishlistProductQuery, wishlistId, productId);

        // Redirect til ønskelisten, så brugeren kan se produktet i deres ønskeliste
        return "redirect:/showWishlist?wishlistId=" + wishlistId;
    }
}

