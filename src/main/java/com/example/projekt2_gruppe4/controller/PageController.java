package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.model.User;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    ProductRepository productRepo;

    @GetMapping("")
    public String mainPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

    @GetMapping("/about")
    public String aboutPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "about";
    }

    @GetMapping("/contactUs")
    public String contactPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "contactUs";
    }

    @PostMapping("/contactUs")
    public String handleContactForm(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String message,
                                    Model model,
                                    HttpSession session) {
        System.out.println("Contact form submitted by: " + name + ", Email: " + email);
        System.out.println("Message: " + message);
        model.addAttribute("contactSuccess", "Thanks for your message, " + name + "! We'll get back to you shortly!.");
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "contactUs"; //
    }


    @GetMapping("/showWishlist")
    public String showWishListPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        List<Product> productList = productRepo.getAllProducts();
        model.addAttribute("productList", productList);
        return "showWishlist";
    }

    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

    @GetMapping("/all-products")
    public String showAllProducts(Model model) {
        List<Product> products = productRepo.getAllProducts();
        model.addAttribute("products", products);
        return "allProductsPage";
    }
}