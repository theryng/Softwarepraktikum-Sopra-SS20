package de.hohenheim.sopraproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventsController {
    @GetMapping("/events")
    public String showHome(Model model) {
        return "events";
    }
}
