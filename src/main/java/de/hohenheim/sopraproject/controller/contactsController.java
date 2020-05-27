package de.hohenheim.sopraproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class contactsController {
    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("message", "Und hier sehen Sie ein Model Attribut");
        return "contacts";
    }
    public void test(){
        System.out.println("Testing");
    }
}