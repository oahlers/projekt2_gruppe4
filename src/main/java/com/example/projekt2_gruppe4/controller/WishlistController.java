package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.model.User;
import com.example.projekt2_gruppe4.model.Wishlist;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import com.example.projekt2_gruppe4.repository.WishlistRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    // Gemmer en ny ønskeliste og tilknytter den til den loggede bruger
    @PostMapping("/saveCreateWishlist")
    public String saveCreateWishlist(@RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("pincode") String pincode,
                                     HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setName(title);
        wishlist.setDescription(description);
        wishlist.setPincode(pincode);
        wishlist.setUserId(loggedInUser.getId());

        wishlistRepository.save(wishlist);

        return "redirect:/wishlists/" + wishlist.getId();
    }

    // Vist en specifik ønskeliste og de produkter der er tilknyttet
    @GetMapping("/{id}")
    public String viewWishlist(@PathVariable("id") int id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        Wishlist wishlist = wishlistRepository.findByIdAndUserId(id, loggedInUser.getId());

        if (wishlist == null) {
            return "redirect:/wishlists/showWishlist";
        }

        model.addAttribute("wishlist", wishlist);

        List<Product> products = productRepository.findByWishlistId(id);
        if (products.isEmpty()) {
            model.addAttribute("message", "Der er ingen produkter i denne ønskeliste.");
        } else {
            model.addAttribute("products", products);
        }

        return "viewWishlist";
    }

    // Vist alle ønskelister for den loggede bruger
    @GetMapping("/showWishlist")
    public String showWishlist(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        List<Wishlist> wishlists = wishlistRepository.findByUserId(loggedInUser.getId());
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("loggedInUser", loggedInUser);

        return "showWishlist";
    }

    // Sletter en ønskeliste
    @PostMapping("/removeWishlist")
    public String removeWishlist(@RequestParam("id") int id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        wishlistRepository.deleteWishlistByIdAndUserId(id, loggedInUser.getId());
        return "redirect:/wishlists/showWishlist";
    }

    // Vist formularen for at redigere en ønskeliste
    @GetMapping("/edit/{id}")
    public String editWishlistForm(@PathVariable("id") int id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        Wishlist wishlist = wishlistRepository.findByIdAndUserId(id, loggedInUser.getId());
        if (wishlist == null) {
            return "redirect:/wishlists/showWishlist";
        }

        model.addAttribute("wishlist", wishlist);

        List<Product> products = productRepository.findByWishlistId(id);
        model.addAttribute("products", products);

        return "editWishlist";
    }

    // Opdaterer en eksisterende ønskeliste
    @PostMapping("/edit/{id}")
    public String updateWishlist(@PathVariable("id") int id,
                                 @RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("pincode") String pincode,
                                 HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/index";
        }

        Wishlist updatedWishlist = new Wishlist();
        updatedWishlist.setId(id);
        updatedWishlist.setName(title);
        updatedWishlist.setDescription(description);
        updatedWishlist.setPincode(pincode);
        updatedWishlist.setUserId(loggedInUser.getId());

        wishlistRepository.update(updatedWishlist);
        return "redirect:/wishlists/" + id;
    }
}
