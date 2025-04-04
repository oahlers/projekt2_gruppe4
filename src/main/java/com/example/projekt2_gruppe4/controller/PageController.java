package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;  // Ændret fra ArrayList til List
@Controller
@RequestMapping("/")  // Dette handler stadig root-path og relaterede sider
public class PageController {

    @Autowired
    ProductRepository productRepo;

    @GetMapping("")
    public String mainPage() {
        return "index";  // Hjemmesiden
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";  // Om os
    }

    @GetMapping("/contactUs")
    public String contactPage() {
        return "contactUs";  // Kontakt os
    }

    @GetMapping("/showWishlist")
    public String showWishListPage(Model model) {
        List<Product> productList = productRepo.getAllProducts();  // Ændret til List

        System.out.println("Antal produkter hentet: " + productList.size());
        for (Product p : productList) {
            System.out.println("Produkt: " + p.getName() + ", Pris: " + p.getPrice());
        }

        model.addAttribute("productList", productList);
        return "showWishlist";  // Ønsker en liste over produkter
    }
}
