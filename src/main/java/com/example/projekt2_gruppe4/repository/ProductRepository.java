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

    /*public Product getProductById(int id){
        for (Product product: initData.getProductList()) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void deleteProduct(int id){
        Product product = getProductById(id);
        initData.getProductList().remove(product);
    }

    public void saveProduct(Product product){
        ArrayList<Product> productList = initData.getProductList();
        int newID;

        if (productList.isEmpty()) {
            newID = 1;
        }else {
            newID = productList.getLast().getId() +1;
        }

        product.setId(newID);
        productList.add(product);
    }

    public void update (Product updatedProduct){
         ArrayList<Product> productList = initData.getProductList();

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId() == updatedProduct.getId()) {
                productList.set(i, updatedProduct);
                return;
            }
            
        }

    }*/

}
