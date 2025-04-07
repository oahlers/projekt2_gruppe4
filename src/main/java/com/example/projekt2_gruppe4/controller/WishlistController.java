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

        String insertQuery = "INSERT INTO wishlist (title, description, pincode, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertQuery, title, description, pincode, loggedInUser.getId());

        String lastInsertIdQuery = "SELECT LAST_INSERT_ID()";
        int wishlistId = jdbcTemplate.queryForObject(lastInsertIdQuery, Integer.class);

        return "redirect:/wishlists/" + wishlistId;
    }

    @GetMapping("/{id}")
    public String viewWishlist(@PathVariable("id") int id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

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

        Wishlist wishlist = wishlists.isEmpty() ? null : wishlists.get(0);

        if (wishlist == null) {
            return "redirect:/wishlists";
        }

        model.addAttribute("wishlist", wishlist);
        return "viewWishlist";
    }
}