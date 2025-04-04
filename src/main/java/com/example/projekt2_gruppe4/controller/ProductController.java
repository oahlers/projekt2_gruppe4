package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.model.User;
import com.example.projekt2_gruppe4.model.Wishlist;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import com.example.projekt2_gruppe4.repository.WishlistRepository;
import com.example.projekt2_gruppe4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private ProductService productService;

    @GetMapping("/getCreateProduct")
    public String createProduct() {
        return "createProduct";
    }

    @PostMapping("/saveCreateProduct")
    public String postCreateProduct(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("price") double price) {
        String img = null;
        Product product = new Product(name, description, price, img);
        productRepo.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/getUpdateProduct")
    public String updateProduct(@RequestParam("id") int id, Model model) throws SQLException {
        Product product = productRepo.getProductById(id);
        model.addAttribute("product", product); // Rettet til at bruge nøgle
        return "updateProduct";
    }

    @PostMapping("/saveUpdateProduct")
    public String postUpdateProduct(@RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("price") double price) {
        String img = productService.getImg(name, description); // Placeholder - tjek om ProductService virker
        Product product = new Product(id, name, description, price, img);
        productRepo.update(product);
        return "redirect:/";
    }

    @GetMapping("/showProduct")
    public String showProduct(@RequestParam("id") int id, Model model) throws SQLException {
        Product product = productRepo.getProductById(id);
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int id) {
        productRepo.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/products")
    public String showProductList(Model model) {
        List<Product> productList = productRepo.getAllProducts();
        model.addAttribute("productList", productList);
        return "index"; // Or a different template, e.g., "product-list"
    }

    @GetMapping("/wishlists")
    public String showWishlists(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth";
        }
        List<Wishlist> wishlists = wishlistRepo.findByUserId(loggedInUser.getId());
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("username", loggedInUser.getUsername());
        return "wishlists";
    }
}