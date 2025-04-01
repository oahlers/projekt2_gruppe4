package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.config.InitData;
import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PageController {

    @Autowired
    InitData initData;

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
