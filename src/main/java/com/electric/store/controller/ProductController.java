package com.electric.store.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.electric.store.model.Product;
import com.electric.store.model.ProductStorage;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {

    @GetMapping("/")
    public String productList(Model model) {
        model.addAttribute("products", ProductStorage.getProducts());
        return "product-list";
    }

    @GetMapping("/create-form")
    public String createForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String create(Product product) {
        product.setId(UUID.randomUUID().toString());
        ProductStorage.getProducts().add(product);
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        Product productToEdit = ProductStorage.getProducts().stream().filter(product -> product.getId().equals(id))
                .findFirst().orElseThrow(RuntimeException::new);
        model.addAttribute("product", productToEdit);
        return "edit-form";
    }

    @PostMapping("/update")
    public String update(Product productToEdit) {
        for (Product productInside : ProductStorage.getProducts()) {
            if (productInside.getId().equals(productToEdit.getId())) {
                productInside.setName(productToEdit.getName());
                productInside.setPrice(productToEdit.getPrice());
            }
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable("id") String id) {
        Product productToDelete = ProductStorage.getProducts().stream().filter(product -> product.getId().equals(id)).findFirst().orElseThrow(RuntimeException::new);
        ProductStorage.getProducts().remove(productToDelete);
        return "redirect:/";
    }
    

}
