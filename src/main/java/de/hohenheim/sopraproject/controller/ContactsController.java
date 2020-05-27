package de.hohenheim.sopraproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {
    @GetMapping("/contacts")
    public String contacts(Model model) {
        return "contacts";
    }

}