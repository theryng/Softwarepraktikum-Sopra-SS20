package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.entity.Address;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {


    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String projects(Model model) {
        List<Project> allProjects = projectService.findAllProjects();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAllProjects(allProjects);
        projectDTO.setProject(new Project());
        model.addAttribute("projectDTO", projectDTO);
        return "projects";
    }

    /**
     * This method saves a new Project
     *
     * This method saves a newly created project to the database. Once the new project was saved to the database the page will
     * be reloaded and the table will be updated. The new project will now show up on the page projects.
     *
     * @param
     * @return redirect:/projects
     */
    @RequestMapping(value="/saveProject", method = RequestMethod.POST)
    public String saveProject(@Valid ProjectDTO projectDTO, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
        }
        else{
            projectService.saveProject(projectDTO.getProject());

        }
        return "redirect:/projects";
    }

    /**
     * This method finds all projects.
     *
     * This method finds all existing projects and returns them to the user
     *
     * @param model
     * @return contacts
     */
    @RequestMapping("/allProjects")
    public String allProjects(Model model) {
        model.addAttribute("allProjects", projectService.findAllProjects());
        return "projects";
    }


    /**
     *  Method which can be used to search for a certain Project.
     *  Calls the Contact Finder, and uses a searchWord to find a Project.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchProject", method = RequestMethod.POST)
    public String searchProjects(String searchWord) {
        /*ContactFinder findContact = new ContactFinder();
        Set<Project> foundProjectsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
        }
        else{
            foundContacts.clear();
        }*/
        return "redirect:/contacts";
    }
}

