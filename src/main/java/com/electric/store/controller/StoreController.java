package com.electric.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StoreController {
    
    @GetMapping("/store")
    public String returnIndex(@RequestParam(name="tools", defaultValue="true", required=false) String param, Model model) {
        model.addAttribute(param, model);
        return "index";
    }
    

}
