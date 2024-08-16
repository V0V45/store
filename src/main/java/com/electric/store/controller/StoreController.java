package com.electric.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.electric.store.model.ProductStorage;

@Controller
public class StoreController {
    @GetMapping("/store")
    public String returnIndex(@RequestParam(name="show", defaultValue="all", required=false) String param, Model model) {
        for (int i = 0; i < ProductStorage.getProducts().size(); i++) {
            model.addAttribute(String.format("product%s", i + 1), ProductStorage.getProducts().get(i).toString());
        }
        if (param.equals("2")) {
            model.addAttribute("shows", "2");
            return "index";
        } else if (param.equals("1")) {
            model.addAttribute("shows", "1");
            return "index";
        } else {
            model.addAttribute("shows", "все");
            return "index";
        }
    }
    

}
