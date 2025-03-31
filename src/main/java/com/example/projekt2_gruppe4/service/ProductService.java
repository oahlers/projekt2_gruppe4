package com.example.projekt2_gruppe4.service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public String getImg(String name, String description) {
        String img = null;

        if (name.contains("cola")) {
            img = "cola.jpg";
        } else if (description.contains("cider")) {
            if (description.contains("cider") || description.contains("cider")) {
                img = "cider.jpg";
            } else {
                img = "faxe.jpg";
            }
        } else if (name.contains("sprite")) {
            img = "sprite.jpg";
        } else {
            img = "øl.jpg";
        }

        return img;
    }
}
