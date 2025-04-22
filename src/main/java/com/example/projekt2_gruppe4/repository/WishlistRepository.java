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

    // Finder en ønskeliste baseret på ønskeliste-ID og bruger-ID
    public Wishlist findByIdAndUserId(int id, int userId) {
        String query = "SELECT * FROM wishlists WHERE id = ? AND user_id = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(query, ps -> {
            ps.setInt(1, id);
            ps.setInt(2, userId);
        }, new WishlistRowMapper());
        return wishlists.isEmpty() ? null : wishlists.get(0);
    }

    // Finder alle ønskelister for en given bruger-ID
    public List<Wishlist> findByUserId(int userId) {
        String query = "SELECT * FROM wishlists WHERE user_id = ?";
        return jdbcTemplate.query(query, ps -> ps.setInt(1, userId), new WishlistRowMapper());
    }

    // Finder en ønskeliste baseret på dens delingstoken og pinkode
    public Wishlist findByShareTokenAndPincode(String shareToken, String pincode) {
        String query = "SELECT * FROM wishlists WHERE share_token = ? AND pincode = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(query, new Object[]{shareToken, pincode}, new WishlistRowMapper());
        return wishlists.isEmpty() ? null : wishlists.get(0);
    }

    // Gemmer en ny ønskeliste i databasen
    public void save(Wishlist wishlist) {
        String query = "INSERT INTO wishlists (name, description, pincode, user_id, share_token) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, wishlist.getName(), wishlist.getDescription(), wishlist.getPincode(),
                wishlist.getUserId(), wishlist.getShareToken());
    }

    // Sletter en ønskeliste baseret på ønskeliste-ID og bruger-ID
    public void deleteWishlistByIdAndUserId(int id, int userId) {
        String query = "DELETE FROM wishlists WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, id, userId);
    }

    // Opdaterer en eksisterende ønskeliste
    public void update(Wishlist wishlist) {
        String query = "UPDATE wishlists SET name = ?, description = ?, pincode = ?, share_token = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(query, wishlist.getName(), wishlist.getDescription(), wishlist.getPincode(),
                wishlist.getShareToken(), wishlist.getId(), wishlist.getUserId());
    }

    // RowMapper for at konvertere ResultSet til en Wishlist
    private static class WishlistRowMapper implements RowMapper<Wishlist> {
        @Override
        public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wishlist wishlist = new Wishlist();
            wishlist.setId(rs.getInt("id"));
            wishlist.setName(rs.getString("name"));  // Ændret fra 'title' til 'name'
            wishlist.setDescription(rs.getString("description"));
            wishlist.setPincode(rs.getString("pincode"));
            wishlist.setUserId(rs.getInt("user_id"));
            wishlist.setShareToken(rs.getString("share_token"));
            return wishlist;
        }
    }
}
