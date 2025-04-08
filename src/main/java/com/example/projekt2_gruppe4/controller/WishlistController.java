package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.User;
import com.example.projekt2_gruppe4.model.Wishlist;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/saveCreateWishlist")
    public String saveCreateWishlist(@RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("pincode") String pincode,
                                     HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }
        String shareToken = UUID.randomUUID().toString().substring(0, 8); // eks: "abc123ef"

        String insertQuery = "INSERT INTO wishlist (title, description, pincode, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, title, description, pincode, loggedInUser.getId());

        String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
        int wishlistId = jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class);

        return "redirect:/wishlists/" + wishlistId;
    }

    @GetMapping("/{id}")
    public String viewWishlist(@PathVariable("id") int id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // Tjek om brugeren er logget ind.
        if (loggedInUser == null) {
            return "redirect:/index"; // Redirect til loginside, hvis ikke logget ind.
        }

        // Hent ønskelisten fra databasen for en given bruger og ID.
        String selectQuery = "SELECT * FROM wishlist WHERE id = ? AND user_id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(selectQuery, new Object[]{id, loggedInUser.getId()}, new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(rs.getInt("id"));
                wishlist.setTitle(rs.getString("title"));
                wishlist.setDescription(rs.getString("description"));
                wishlist.setPincode(rs.getString("pincode"));
                wishlist.setUserId(rs.getInt("user_id"));
                return wishlist;
            }
        });

        // Hvis listen er tom, returner til ønskelisteoversigten.
        if (wishlists.isEmpty()) {
            return "redirect:/wishlists";
        }

        Wishlist wishlist = wishlists.get(0);
        model.addAttribute("wishlist", wishlist); // Tilføj data til Thymeleaf-modellen.

        return "viewWishlist"; // Return viewWishlist.html i resources/templates.
    }

    @GetMapping("/shared/{token}")
    public String showPincodeForm(@PathVariable("token") String token, Model model) {
        model.addAttribute("token", token);
        return "enterPincode"; // En side hvor man indtaster pinkoden
    }

    @PostMapping("/shared")
    public String viewSharedWishlist(@RequestParam("token") String token,
                                     @RequestParam("pincode") String pincode,
                                     Model model) {
        String sql = "SELECT * FROM wishlists WHERE share_token = ? AND pincode = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, new Object[]{token, pincode}, new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(rs.getInt("id"));
                wishlist.setUserId(rs.getInt("user_id"));
                wishlist.setTitle(rs.getString("name"));
                wishlist.setDescription(rs.getString("description"));
                wishlist.setPincode(rs.getString("pincode"));
                wishlist.setShareToken(rs.getString("share_token"));
                return wishlist;
            }
        });

        if (wishlists.isEmpty()) {
            model.addAttribute("sharedError", "Forkert delingslink eller pinkode");
            return "index"; // tilbage til forsiden med fejlbesked
        }

        Wishlist wishlist = wishlists.get(0);
        model.addAttribute("wishlist", wishlist);
        return "viewSharedWishlist"; // en side der viser ønskelisten
    }


}