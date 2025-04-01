package com.example.projekt2_gruppe4.config;

import com.example.projekt2_gruppe4.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InitData {

    private ArrayList<Product> productList = new ArrayList<>();

    public InitData() {
        productList.add(new Product(1, "Øl", "Den kan drikkes", 18, "øl.jpg"));
        productList.add(new Product(2, "Cider", "Den kan drikkes", 18, "cider.jpeg"));
        productList.add(new Product(3, "Faxe", "Den kan drikkes", 18, "faxe.jpg"));
        productList.add(new Product(4, "Cola", "Den kan drikkes", 18, "cola.jpg"));
        productList.add(new Product(5, "Sprite", "Den kan drikkes", 18, "sprite.webp"));
    }

    public ArrayList<Product> getProductList() { return productList;
    }
}
