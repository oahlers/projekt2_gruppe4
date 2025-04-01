package com.example.projekt2_gruppe4.controller;

import com.example.projekt2_gruppe4.model.Product;
import com.example.projekt2_gruppe4.repository.ProductRepository;
import com.example.projekt2_gruppe4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    private ProductService productService;

    @GetMapping("/getCreateProduct")
    public String CreateProduct(){
        return "createProduct";
    }

    @PostMapping("/saveCreteProduct")
    public String postCreateProduct(@RequestParam ("name") String name,
                                    @RequestParam ("description") String description,
                                    @RequestParam ("price") double price){
        String img=null;

        //(se video 2) - fixed med temps - images kommer først når .html er lavet//
        Product product = new Product(name, description, price, img);
        productRepo.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/getUpdateProduct")
    public String UpdateProduct(@RequestParam("id") int id, Model model){
        Product product = productRepo.getProductById(id);
        model.addAttribute(product);
        return "updateProduct";
    }

    @PostMapping("/saveUpdateProduct")
    public String postUpdateProduct(@RequestParam ("id") int id,
                                    @RequestParam ("name") String name,
                                    @RequestParam ("description") String description,
                                    @RequestParam ("price") double price) {
        String img = productService.getImg(name, description); //virker først når Service er fixed
        Product product = new Product(id, name, description, price, img);
        productRepo.update(product);
        return "redirect:/";
    }
    

    @GetMapping("/showProduct")
    public String showProduct(@RequestParam("id") int id, Model model) {

        Product product = productRepo.getProductById(id);
        model.addAttribute("product", product);

        return "product";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam ("id") int id) {

        productRepo.deleteProduct(id);

        return "redirect:/";
    }

}
