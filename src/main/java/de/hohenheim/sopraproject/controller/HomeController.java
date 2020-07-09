package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.dto.OccurrenceDTO;
import de.hohenheim.sopraproject.entity.Occurrence;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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

    /**
     * This method saves a new occurrence
     *
     * This method saves a newly created occurrence to the database. Once the new occurrence was saved to the database the page will
     * be reloaded and the table will be updated.
     *
     * @param
     * @return redirect:/home
     */
    @RequestMapping(value="/saveOccurrence", method = RequestMethod.POST)
    public String saveOccurrence(@Valid OccurrenceDTO occurrenceDTO, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
        }
        else{
            occurrenceService.saveOccurrence(occurrenceDTO.getOccurrence());
        }
        return "redirect:/home";
    }

    /**
     * This method finds all occurrences.
     *
     * This method finds all existing occurrence and returns them to the user
     *
     * @param model
     * @return home
     */
    @RequestMapping("/allOccurrences")
    public String allOccurrences(Model model) {
        model.addAttribute("allOccurrences", occurrenceService.findAllOccurrences());
        return "home";
    }


    /**
     *  Method which can be used to search for a certain occurrence.
     *  Calls the Contact Finder, and uses a searchWord to find a occurrence.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return redirect:/contacts
     */
    @RequestMapping(value ="/searchOccurrence", method = RequestMethod.POST)
    public String searchOccurrences(String searchWord) {
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/deleteOccurrence", method = RequestMethod.GET)
    public String deleteOccurrence(OccurrenceDTO occurrenceDTO) {
        occurrenceService.deleteByOccurrenceID(occurrenceDTO.getOccurrenceID());
        return "redirect:/home";
    }
}