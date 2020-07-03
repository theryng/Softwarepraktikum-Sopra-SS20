package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page projectDetails
 *
 * It helps with various method to save new Projects, delete existing ones, open up the ProjectContactCreator.
 *
 * @date 01.07.2020
 * @author Mark Wagner (orientated by work of Lukas Januschke)
 */
@Controller
public class ProjectDetailsController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContactService contactService;



    /**
     * Main method for Viewing of project Details Site, adds necessary Attributes
     * @param model
     * @return projectDetails
     */
    @RequestMapping(value = "/projectDetails/{projectID}", method = RequestMethod.GET)
    public String projectDetails(@PathVariable("projectID") Integer projectID, Model model) {
        System.out.println("Testing the stuff " + projectID);
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = projectService.findByProjectID(projectID);
        projectDTO.setProject(project);
        projectDTO.setProjectID(projectDTO.getProject().getProjectID());
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", checkTables(project));
        return "projects/projectDetails";
    }

    /**
     * This method saves the projects and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing project. As long thats not the case a new project will be saved to the database. Once the project is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param projectDTO
     * @return redirect:/projects
     */
    @RequestMapping(value = "/savingProject", method = RequestMethod.POST)
    public String savingProject(@Valid ProjectDTO projectDTO, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "projects/projectDetails";
        }
        else{
            Project project = projectDTO.getProject();
            project.setProjectID(projectDTO.getProjectID());
            if(!projectService.findByProjectID(project.getProjectID()).equals(project)){
                projectService.saveProject(project);
            }
            projectDTO.setProject(project);
            model.addAttribute("projectDTO", projectDTO);
            return "projects/projectDetails";
        }
    }

    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(ProjectDTO projectDTO) {
        projectService.deleteByProjectID(projectDTO.getProjectID());
        return "redirect:/projects";
    }

    @RequestMapping(value = "/deleteContactFromProject", method = RequestMethod.POST)
    public String deleteContactFromProject(ProjectDTO projectDTO, Model model) {
        Project project = projectService.findByProjectID(projectDTO.getProjectID());
        Set<Contact> contacts = project.getContacts();
        System.out.println(contacts.size());
        Contact deleteContact = contactService.findByContactID(projectDTO.getContactTempID());
        contacts.remove(deleteContact);
        System.out.println(contacts.size());
        project.setContacts(contacts);
        projectService.saveProject(project);
        projectDTO.setProject(project);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", checkTables(project));
        return "projects/projectDetails";
    }

    private boolean checkTables(Project project){
        if(project.getContacts().size()>0){
            return true;
        }
        return false;
    }

}


