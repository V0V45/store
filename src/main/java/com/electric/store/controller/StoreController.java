package com.electric.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StoreController {
    
    @GetMapping("/store")
    public String returnIndex(@RequestParam(name="show", defaultValue="all", required=false) String param, Model model) {
        

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
