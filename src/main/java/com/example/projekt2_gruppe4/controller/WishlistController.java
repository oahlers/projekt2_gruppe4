package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
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
import java.util.ArrayList;
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
        String shareToken = UUID.randomUUID().toString().substring(0, 8);

        String insertQuery = "INSERT INTO wishlists (name, description, pincode, user_id) VALUES (?, ?, ?, ?)";
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

        String selectQuery = "SELECT * FROM wishlists WHERE id = ? AND user_id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(selectQuery, new Object[]{id, loggedInUser.getId()}, new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(rs.getInt("id"));
                wishlist.setTitle(rs.getString("name"));
                wishlist.setDescription(rs.getString("description"));
                wishlist.setPincode(rs.getString("pincode"));
                wishlist.setUserId(rs.getInt("user_id"));
                return wishlist;
            }
        });

        if (wishlists.isEmpty()) {
            return "redirect:/wishlists/showWishlist";
        }

        Wishlist wishlist = wishlists.get(0);
        model.addAttribute("wishlist", wishlist);

        String productQuery = "SELECT p.id, p.name, p.description, p.price " +
                "FROM products p " +
                "JOIN wishlist_products wp ON wp.product_id = p.id " +
                "WHERE wp.wishlist_id = ?";
        List<Product> products = jdbcTemplate.query(productQuery, new Object[]{id}, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                return product;
            }
        });

        model.addAttribute("products", products);

        return "viewWishlist";
    }

    @GetMapping("/showWishlist")
    public String showWishlist(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        List<Wishlist> wishlists;
        try {
            String sql = "SELECT * FROM wishlists WHERE user_id = ?";
            wishlists = jdbcTemplate.query(sql, new Object[]{loggedInUser.getId()}, new RowMapper<Wishlist>() {
                @Override
                public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setId(rs.getInt("id"));
                    wishlist.setTitle(rs.getString("name"));
                    wishlist.setDescription(rs.getString("description"));
                    wishlist.setPincode(rs.getString("pincode"));
                    wishlist.setUserId(rs.getInt("user_id"));
                    wishlist.setShareToken(rs.getString("share_token"));
                    return wishlist;
                }
            });
        } catch (Exception e) {
            System.err.println("Fejl ved hentning af ønskelister: " + e.getMessage());
            wishlists = new ArrayList<>();
        }

        model.addAttribute("wishlists", wishlists);
        model.addAttribute("loggedInUser", loggedInUser);
        return "showWishlist";
    }

    @PostMapping("/removeWishlist")
    public String removeWishlist(@RequestParam("id") int id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        String deleteQuery = "DELETE FROM wishlists WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(deleteQuery, id, loggedInUser.getId());

        return "redirect:/wishlists/showWishlist";
    }

    @GetMapping("/edit/{id}")
    public String editWishlistForm(@PathVariable("id") int id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        String selectQuery = "SELECT * FROM wishlists WHERE id = ? AND user_id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(selectQuery, new Object[]{id, loggedInUser.getId()}, new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(rs.getInt("id"));
                wishlist.setTitle(rs.getString("name"));
                wishlist.setDescription(rs.getString("description"));
                wishlist.setPincode(rs.getString("pincode"));
                wishlist.setUserId(rs.getInt("user_id"));
                return wishlist;
            }
        });

        if (wishlists.isEmpty()) {
            return "redirect:/wishlists/showWishlist";
        }

        Wishlist wishlist = wishlists.get(0);
        model.addAttribute("wishlist", wishlist);

        return "editWishlist";
    }

    @PostMapping("/edit/{id}")
    public String updateWishlist(@PathVariable("id") int id,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("pincode") String pincode,
                                 HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        String updateQuery = "UPDATE wishlists SET name = ?, description = ?, pincode = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(updateQuery, title, description, pincode, id, loggedInUser.getId());

        return "redirect:/wishlists/" + id;
    }

    @GetMapping("/shared/{token}")
    public String showPincodeForm(@PathVariable("token") String token, Model model) {
        model.addAttribute("token", token);
        return "enterPincode";
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
            return "index";
        }

        Wishlist wishlist = wishlists.get(0);
        model.addAttribute("wishlist", wishlist);
        return "viewSharedWishlist";
    }
}
