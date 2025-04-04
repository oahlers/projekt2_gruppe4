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
@RequestMapping("/")
public class PageController {

    @Autowired
    ProductRepository productRepo;

    @GetMapping("")
    public String mainPage(Model model) {
        System.out.println("🔹 mainPage() blev kaldt!");

        ArrayList<Product> productList = productRepo.getAllProducts();

        System.out.println("Antal produkter hentet: " + productList.size());
        for (Product p : productList) {
            System.out.println("Produkt: " + p.getName() + ", Pris: " + p.getPrice());
        }

        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // matcher about.html i templates-mappen
    }

    @GetMapping("/ContactUs")
    public String contactPage() {
        return "contactUs"; // matcher about.html i templates-mappen
    }
}
