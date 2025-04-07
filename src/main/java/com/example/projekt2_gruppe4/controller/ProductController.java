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

    /**
     * Viser en side, hvor der kan oprettes et nyt produkt.
     */
    @GetMapping("/getCreateProduct")
    public String createProduct() {
        return "createProduct"; // Passer med din HTML-fil "createProduct.html"
    }

    /**
     * Gemmer et nyt produkt i databasen.
     *
     * @param name        Navnet på produktet.
     * @param description Beskrivelse af produktet.
     * @param price       Pris på produktet.
     * @return Redirecter til forsiden.
     */
    @PostMapping("/saveCreateProduct")
    public String postCreateProduct(@RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("price") double price) {
        String img = null; // Kan ændres til en standardværdi, hvis nødvendigt.

        // Validering af input (optional, men kan tilføjes for bedre sikkerhed)
        if (name.isEmpty() || description.isEmpty() || price <= 0) {
            // Hvis input er ugyldigt, kan vi enten returnere med en fejlbesked eller håndtere på anden vis.
            return "redirect:/getCreateProduct?error=true";
        }

        // Skaber et nyt produkt og gemmer det.
        Product product = new Product(name, description, price, img);
        productRepo.saveProduct(product);

        return "redirect:/"; // Omdirigerer til forsiden.
    }

    /**
     * Henter detaljer for et produkt til opdatering.
     *
     * @param id    ID for produktet.
     * @param model Model, der bruges til at sende data til viewet.
     * @return Returnerer en HTML-side, hvor produktet kan opdateres.
     * @throws SQLException Hvis der opstår en problem med databasen.
     */
    @GetMapping("/getUpdateProduct")
    public String updateProduct(@RequestParam("id") int id, Model model) throws SQLException {
        // Henter et produkt via dets ID.
        Product product = productRepo.getProductById(id);

        // Tjekker, om produktet findes. Hvis ikke, redirect til en fejlside eller tilbage til produktlisten.
        if (product == null) {
            return "redirect:/products?error=notfound";
        }

        // Tilføjer produktet som attribut i modellen, så det kan bruges i HTML-siden.
        model.addAttribute("product", product);
        return "updateProduct"; // Passer med din HTML-fil (fx "updateProduct.html").
    }

    /**
     * Opdaterer et produkt baseret på de indtastede data.
     */
    @PostMapping("/saveUpdateProduct")
    public String postUpdateProduct(@RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("price") double price) {
        // Simple inputvalidering for at sikre, at dataene er gyldige.
        if (name.isEmpty() || description.isEmpty() || price <= 0) {
            return "redirect:/getUpdateProduct?id=" + id + "&error=true";
        }

        // Henter det eksisterende billede (kan revideres afhængigt af, hvordan image håndteres).
        String img = productService.getImg(name, description);

        // Skaber en opdateret produktinstans og gemmer ændringer.
        Product product = new Product(id, name, description, price, img);
        productRepo.update(product);

        return "redirect:/products"; // Omdirigerer til produktlisten.
    }

    /**
     * Viser detaljer for et specifikt produkt.
     */
    @GetMapping("/showProduct")
    public String showProduct(@RequestParam("id") int id, Model model) throws SQLException {
        // Henter produktet baseret på ID.
        Product product = productRepo.getProductById(id);

        if (product == null) {
            return "redirect:/products?error=notfound"; // Fejlhåndtering, hvis produktet ikke findes.
        }

        model.addAttribute("product", product);
        return "product"; // Returnerer en HTML-side for produktdetaljer (fx "product.html").
    }

    /**
     * Sletter et produkt ved hjælp af produkt-ID.
     */
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") int id) {
        // Før vi sletter produktet, kan vi tjekke, om det eksisterer, hvis nødvendigt.
        productRepo.deleteProduct(id);
        return "redirect:/products"; // Omdirigerer til produktlisten.
    }

    /**
     * Viser en liste over alle produkter.
     */
    @GetMapping("/products")
    public String showProductList(Model model) {
        // Henter alle produkter fra databasen.
        List<Product> productList = productRepo.getAllProducts();

        // Tjekker for tomme lister (valgfrit, hvis du har særlige regler ved ingen produkter).
        model.addAttribute("productList", productList);

        return "index"; // Returnerer siden med produktlisten (fx "index.html").
    }

    /**
     * Viser alle ønskelister for en bruger.
     */
    @GetMapping("/wishlists")
    public String showWishlists(HttpSession session, Model model) {
        // Finder den logged-in bruger.
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/auth"; // Redirect til login, hvis brugeren ikke er logget ind.
        }

        // Henter brugerens ønskelister.
        List<Wishlist> wishlists = wishlistRepo.findByUserId(loggedInUser.getId());

        model.addAttribute("wishlists", wishlists); // Tilføjer ønskelisterne til modellen.
        model.addAttribute("username", loggedInUser.getUsername()); // Tilføjer brugernavn til visning.

        return "wishlists"; // Returnerer siden med ønskelisterne (fx "wishlists.html").
    }
}