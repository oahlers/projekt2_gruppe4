package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class PageController {

    @Autowired
    ProductRepository productRepo;

    @GetMapping("")
    public String mainPage(Model model) {
        ArrayList<Product> productList = new ArrayList<>();
        productList = productRepo.getAllProducts();
        model.addAttribute("productList", productList);


        return "index";
    }
}
