package com.example.projekt2_gruppe4.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.naming.AuthenticationException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DatabaseAuthenticator {
    private static DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        DatabaseAuthenticator.dataSource = dataSource;
    }

    public static int authenticate(String username, String password) throws AuthenticationException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        throw new AuthenticationException("Invald username and password");
    }
}

