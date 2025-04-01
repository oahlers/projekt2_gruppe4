package com.example.projekt2_gruppe4.repository;

import com.example.projekt2_gruppe4.config.InitData;
import com.example.projekt2_gruppe4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class ProductRepository {

    @Autowired
    InitData initData;

    @Autowired
    private DataSource dataSource;
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> tempProductList = new ArrayList<>();

        String sql = "SELECT * FROM product";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setImage(resultSet.getString("img"));
                tempProductList.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tempProductList;
    }

    public Product getProductById(int id){
        Product product = new Product();
        String sql = "SELECT * FROM product WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setImage(resultSet.getString("img"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }return product;
    }

    public void deleteProduct(int id){
        String sql = "DELETE FROM product WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveProduct(Product product){
        String sql = "INSERT INTO products (name, description, price, img) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            //forstår ikke fejlen her. hjælp gerne?? (video 4 omkring min10)
            statement.setString(product.getName());
            statement.setString(product.getDescription());
            statement.setDouble(product.getPrice());
            statement.setString(product.getImage());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update (Product updatedProduct){
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, img = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(updatedProduct.getName());
            statement.setString(updatedProduct.getDescription());
            statement.setDouble(updatedProduct.getPrice());
            statement.setString(updatedProduct.getImage());
            statement.setInt(updatedProduct.getId());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
