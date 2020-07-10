package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.OccurrenceDTO;
import de.hohenheim.sopraproject.entity.Occurrence;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private EditingHistoryRepository editingHistoryRepository;

    @Autowired
    private OccurrenceService occurrenceService;

    /**
     * Zeigt die Startseite Ihrer Anwendung.
     * @param model enthält alle ModelAttribute.
     * @return home-Seite.
     */
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("message", "Und hier sehen Sie ein Model Attribut");
        model.addAttribute("editingHistory", editingHistoryRepository.findAll());

        List<Occurrence> allUpcomingOccurrences = occurrenceService.findtAllUpcomingOccurrences();
        OccurrenceDTO occurrenceDTO = new OccurrenceDTO();
        occurrenceDTO.setAllUpcomingOccurrences(allUpcomingOccurrences);
        occurrenceDTO.setOccurrence(new Occurrence());
        model.addAttribute("occurrenceDTO", occurrenceDTO);

        return "home";
    }
}