package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactHistoryService;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ProjectSelectorController {

    @Autowired
    private ContactHistoryService contactHistoryService;

    @Autowired
    private ProjectService projectService;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return relationshipCreator1
     */
    @GetMapping("/projectSelector/{contactHistoryID}/{contactID}")
    public String ProjectSelectorController(@PathVariable("contactID") Integer contactID, @PathVariable("contactHistoryID") Integer contactHistoryID, Model model) {
        ContactHistoryDTO contactHistoryDTO = new ContactHistoryDTO();
        contactHistoryDTO.setOriginalContactID(contactID+"");
        contactHistoryDTO.setOriginalContactHistoryID(contactHistoryID);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        model.addAttribute("viewTable", false);

        return "contacts/projectSelector";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param contactHistoryDTO
     * @return relationshipCreator1
     */
    @RequestMapping(value ="/searchContactHistoryProject", method = RequestMethod.POST)
    public String searchContactHistoryProject(ContactHistoryDTO contactHistoryDTO, Model model) {
        System.out.println(contactHistoryDTO.getSearchWord());
        String searchWord = contactHistoryDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Project> foundProjectsTemp = findContact.findProjects(searchWord, projectService.findAllProjects());
        contactHistoryDTO.setAllProjects(foundProjectsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", contactHistoryDTO);
        return "contacts/projectSelector";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setContactHistoryProject", method = RequestMethod.POST)
    public String setContactHistoryProject(ContactHistoryDTO contactHistoryDTO, RedirectAttributes redirectAttributes) {
        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getOriginalContactHistoryID());
        Project project = projectService.findByProjectID(Integer.valueOf(contactHistoryDTO.getEventID()));
        project.getContactHistories().add(contactHistory);
        contactHistory.setProject(project);
        contactHistoryService.saveContacthistory(contactHistory);
        projectService.saveProject(project);
        return "redirect:/contactHistoryEditor/"+contactHistoryDTO.getOriginalContactID()+"/"+contactHistoryDTO.getOriginalContactHistoryID();
    }

}