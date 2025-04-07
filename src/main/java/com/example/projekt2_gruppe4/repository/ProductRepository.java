package com.example.projekt2_gruppe4.repository;

import com.example.projekt2_gruppe4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Hent alle produkter tilknyttet en ønskeliste
    public List<Product> findByWishlistId(int wishlistId) {
        String query = """
            SELECT p.id, p.name, p.description, p.price, p.img
            FROM wishlist_products wp
            JOIN products p ON wp.product_id = p.id
            WHERE wp.wishlist_id = ?
        """;

        return jdbcTemplate.query(query, new Object[]{wishlistId}, new RowMapper<Product>() {
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
        });
    }

    // Gem et nyt produkt
    public void save(Product product) {
        String query = "INSERT INTO products (name, description, price, img) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, product.getName(), product.getDescription(), product.getPrice(), product.getImage());
    }

    // Opdater et eksisterende produkt
    public void update(Product updatedProduct) {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, img = ? WHERE id = ?";
        jdbcTemplate.update(query, updatedProduct.getName(), updatedProduct.getDescription(),
                updatedProduct.getPrice(), updatedProduct.getImage(), updatedProduct.getId());
    }

    // Hent alle produkter fra databasen
    public List<Product> getAllProducts() {
        String query = "SELECT * FROM products";
        return jdbcTemplate.query(query, new RowMapper<Product>() {
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
        });
    }
}