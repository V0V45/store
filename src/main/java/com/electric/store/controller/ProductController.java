package com.electric.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.electric.store.dao.ProductDao;
import com.electric.store.model.Product;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {
    private ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/")
    public String productList(Model model) {
        model.addAttribute("products", productDao.findAll());
        return "product-list";
    }

    @GetMapping("/create-form")
    public String createForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String create(Product product) {
        productDao.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit-form/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        Product productToEdit = productDao.getById(id);
        model.addAttribute("product", productToEdit);
        return "edit-form";
    }

    @PostMapping("/update")
    public String update(Product productToEdit) {
        productDao.update(productToEdit);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String removeProduct(@PathVariable("id") String id) {
        Product productToDelete = productDao.getById(id);
        productDao.delete(productToDelete);
        return "redirect:/";
    }
    

}
