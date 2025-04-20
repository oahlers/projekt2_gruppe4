package com.example.projekt2_gruppe4.repository;

import com.example.projekt2_gruppe4.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Opretter en ny bruger i databasen
    public void createUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    // Henter en bruger fra databasen baseret på brugernavn
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserRowMapper());
        } catch (Exception e) {
            return null; // Returnerer null, hvis brugeren ikke findes
        }
    }

    // RowMapper til at konvertere ResultSet til et User-objekt
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            int userId = rs.getInt("id");
            user.setId(userId);
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            System.out.println("Mapped user: " + user.getUsername() + ", ID: " + user.getId());
            return user;
        }
    }
}
