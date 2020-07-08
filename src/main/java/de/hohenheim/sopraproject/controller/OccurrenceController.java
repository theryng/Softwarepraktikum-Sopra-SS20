//package de.hohenheim.sopraproject.controller;
//
//import de.hohenheim.sopraproject.dto.OccurrenceDTO;
//import de.hohenheim.sopraproject.entity.Occurrence;
//import de.hohenheim.sopraproject.entity.Project;
//import de.hohenheim.sopraproject.service.ContactService;
//import de.hohenheim.sopraproject.service.OccurrenceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Clock;
//import java.time.LocalDate;
//import java.util.LinkedList;
//import java.util.List;
//
//@Controller
//public class OccurrenceController {
//
//    @Autowired
//    OccurrenceService occurrenceService;
//
//    @RequestMapping(value ="/home", method = RequestMethod.GET)
//    public String occurrences(Model model) {
//        List<Occurrence> allUpcomingOccurrences = occurrenceService.findtAllUpcomingOccurrences();
//        OccurrenceDTO occurrenceDTO = new OccurrenceDTO();
//        occurrenceDTO.setAllUpcomingOccurrences(allUpcomingOccurrences);
//        occurrenceDTO.setOccurrence(new Occurrence());
//        model.addAttribute("occurrenceDTO", occurrenceDTO);
//        return "home";
//    }
//
//    /**
//     * This method finds all upcoming occurrences.
//     *
//     * This method finds all upcoming occurrences and returns them to the user
//     *
//     * @param model
//     * @return contacts
//     */
//    @RequestMapping("/allUpcomingOccurrences")
//    public String allUpcomingOccurrences(Model model) {
//        model.addAttribute("allUpcomingOccurrences", occurrenceService.findtAllUpcomingOccurrences());
//        return "home";
//    }
//
//    /**
//     * This method finds all projects.
//     *
//     * This method finds all existing projects and returns them to the user
//     *
//     * @param model
//     * @return contacts
//     */
////    @RequestMapping("/allProjects")
////    public String allProjects(Model model) {
////        model.addAttribute("allProjects", projectService.findAllProjects());
////        return "projects";
////    }
//}
