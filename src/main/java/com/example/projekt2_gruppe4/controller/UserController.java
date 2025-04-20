package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.User;
import com.example.projekt2_gruppe4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Behandler loginformularen, validerer brugernavn og adgangskode, og opretter session for brugeren
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            model.addAttribute("loggedInUser", user);
            return "redirect:/wishlists/showWishlist";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "index";
        }
    }

    // Behandler registreringsformularen, opretter en ny bruger i databasen
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {
        if (password.length() < 8) {
            model.addAttribute("registerError", "Adgangskoden skal være mindst 8 tegn.");
            return "login";
        }
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("registerError", "Username already exists");
            return "login";
        } else {
            User newUser = new User(username, password);
            userRepository.createUser(newUser);
            return "redirect:/auth";
        }
    }

    // Referering til index siden efter oprettelse af bruger
    @GetMapping("/auth")
    public String showAuthPage() {
        return "index";
    }
}
