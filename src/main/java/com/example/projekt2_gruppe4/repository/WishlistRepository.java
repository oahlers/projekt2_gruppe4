package com.example.projekt2_gruppe4.repository;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishlistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Wishlist findById(int id) {
        String query = "SELECT * FROM wishlist WHERE id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(query, ps -> ps.setInt(1, id), new RowMapper<Wishlist>() {
            @Override
            public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Wishlist(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                        rs.getString("pincode"), rs.getInt("user_id"));
            }
        });

        return wishlists.isEmpty() ? null : wishlists.get(0);
    }

    public List<Product> findProductsByWishlistId(int wishlistId) {
        String query = "SELECT * FROM product WHERE wishlist_id = ?";
        return jdbcTemplate.query(query, ps -> ps.setInt(1, wishlistId), new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setWishlistId(rs.getInt("wishlist_id"));
                return product;
            }
        });
    }

    public void save(Wishlist wishlist) {
        String query = "INSERT INTO wishlist (title, description, pincode, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, wishlist.getTitle(), wishlist.getDescription(), wishlist.getPincode(), wishlist.getUserId());
    }
}