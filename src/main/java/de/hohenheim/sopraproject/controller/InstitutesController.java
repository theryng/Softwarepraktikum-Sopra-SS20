package de.hohenheim.sopraproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InstitutesController {
    @GetMapping("/institutes")
    public String institutes(Model model) {
        return "home";
    }
}
