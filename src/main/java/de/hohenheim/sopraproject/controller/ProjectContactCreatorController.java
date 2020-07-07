package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.service.ContactFinder;
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
 */
@Controller
public class ProjectContactCreatorController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ProjectService projectService;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return projects/projectContactCreator
     */
    @GetMapping("/projectContactCreator/{contactID}")
    public String projectContactCreator(@PathVariable("contactID") Integer contactID, Model model) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectID(contactID);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", false);

        return "projects/projectContactCreator";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param projectDTO
     * @return projects/projectContactCreator
     */
    @RequestMapping(value ="/searchProjectContact", method = RequestMethod.POST)
    public String searchProjectContact(ProjectDTO projectDTO, Model model) {
        System.out.println(projectDTO.getSearchWord());
        System.out.println(projectDTO.getProjectID());
        String searchWord = projectDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactService.findAllContacts(), "Name");
        projectDTO.setFoundProjects(foundContactsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", projectDTO);
        return "projects/projectContactCreator";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setProjectContact", method = RequestMethod.POST)
    public String setProjectContact(ProjectDTO projectDTO) {
        System.out.println("IDS für die Project:");
        System.out.println(projectDTO.getContactTempID());
        System.out.println(projectDTO.getProjectID());
        Project project = projectService.findByProjectID(projectDTO.getProjectID());
        project.addProjectContacts(contactService.findByContactID(projectDTO.getContactTempID()));
        projectService.saveProject(project);
        return "redirect:/projectDetails/"+projectDTO.getProjectID();
    }
}
