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

    // Viser forsiden og tilføjer den aktuelle bruger til modellen
    @GetMapping("")
    public String mainPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

    // Viser "Om os"-siden og tilføjer den aktuelle bruger til modellen
    @GetMapping("/about")
    public String aboutPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "about";
    }

    // Viser "Kontakt os"-siden og tilføjer den aktuelle bruger til modellen
    @GetMapping("/contactUs")
    public String contactPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "contactUs";
    }

    // Håndterer formularindsendelse fra "Kontakt os"-siden og viser en bekræftelsesbesked
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
        return "contactUs";
    }

    // Viser ønskelisten og henter alle produkter fra databasen
    @GetMapping("/showWishlist")
    public String showWishListPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        List<Product> productList = productRepo.getAllProducts();
        model.addAttribute("productList", productList);
        return "showWishlist";
    }

    // Ekstra indgang til forsiden
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "index";
    }

}
