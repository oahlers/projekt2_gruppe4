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

    public List<Wishlist> findByUserId(int userId) {
        String sql = "SELECT * FROM wishlists WHERE user_id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, new Object[]{userId}, new WishlistRowMapper());
        for (Wishlist wishlist : wishlists) {
            wishlist.setProducts(getProductsForWishlist(wishlist.getId()));
        }
        return wishlists;
    }

    private List<Product> getProductsForWishlist(int wishlistId) {
        String sql = "SELECT p.* FROM products p JOIN wishlist_products wp ON p.id = wp.product_id WHERE wp.wishlist_id = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, new ProductRowMapper());
    }

    private static class WishlistRowMapper implements RowMapper<Wishlist> {
        @Override
        public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Wishlist(rs.getInt("id"), rs.getInt("user_id"), rs.getString("name"));
        }
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setImage(rs.getString("img"));
            return product;
        }
    }

    public Wishlist findById(int id) {
        // Eksempel, hvor vi finder ønskelisten vha. et SQL-kald
        Wishlist wishlist = jdbcTemplate.queryForObject(
                "SELECT * FROM wishlists WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Wishlist(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("pincode"),
                        rs.getInt("user_id")
                )
        );

        // Finder tilhørende produkter og sætter det på ønskelisten (valgfrit eksempel)
        List<Product> products = jdbcTemplate.query(
                "SELECT * FROM products WHERE wishlist_id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("img")
                )
        );
        wishlist.setProducts(products); // Bruger 'setProducts' her

        return wishlist;
    }

    public void save(Wishlist wishlist) {
        String sql = "INSERT INTO wishlists (id, title, description, pincode, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, wishlist.getId(), wishlist.getTitle(), wishlist.getDescription(), wishlist.getPincode(), wishlist.getUserId());
    }
}