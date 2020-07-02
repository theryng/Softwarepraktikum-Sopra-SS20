package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private EditingHistoryRepository editingHistoryRepository;
    /**
     * Zeigt die Startseite Ihrer Anwendung.
     * @param model enthält alle ModelAttribute.
     * @return home-Seite.
     */
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("message", "Und hier sehen Sie ein Model Attribut");
        model.addAttribute("editingHistory", editingHistoryRepository.findAll());
        return "home";
    }

}