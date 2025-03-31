package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.config.InitData;
import com.example.projekt2_gruppe4.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PageController {

    @Autowired
    InitData initData;

    @GetMapping("")
    public String mainPage(Model model) {
        ArrayList<Product> productList = new ArrayList<>();

        productList.addAll(initData.getProductList());

        model.addAttribute("productList", productList);

        return "index";
    }
}
