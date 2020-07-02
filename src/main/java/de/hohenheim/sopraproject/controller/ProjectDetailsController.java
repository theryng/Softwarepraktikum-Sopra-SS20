package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import de.hohenheim.sopraproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page projectDetails
 *
 * It helps with various method to save new Projects, delete existing ones, open up the ProjectContactCreator.
 *
 * @date 01.07.2020
 * @author Mark Wagner
 */
@Controller
public class ProjectDetailsController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContactRepository contactService;

    /**
     * Main method for Viewing of project details Site, adds necessary attributes
     * @param model
     * @return projectDetails
     */
    @RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
    public String projectDetails(Integer projectID, Model model, BindingResult result) {
        System.out.println("testing" + projectID);
        Project project = projectService.findByProjectID(projectID);

        if(result.hasErrors()) {
            System.out.println("Fehler");

            model.addAttribute("allProjects", projectService.findAllProjects());

            return "redirect:/projectDetails";
        }

        String searchWord = "";
        model.addAttribute("hasEror", result);
        model.addAttribute("project", project);
        model.addAttribute("projects", new Project());
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("tempContact", new Contact());

        project = projectService.findByProjectID(projectID);
        checkTables(project);


        model.addAttribute("project", project);
        model.addAttribute("projects", new Project());
        model.addAttribute("tempContact", new Contact());

        return "projects/projectDetails";
    }

    /**
     * This method saves the projects and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing projects. As long thats not the case a new projects will be saved to the database. Once the projects is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param project
     * @return redirect:/projectDetails
     */
    @RequestMapping(value = "/savingProjects", method = RequestMethod.POST)
    public String savingProjects(@Valid Project project, BindingResult result, Model model) {
        if(result.hasErrors()){
            System.out.println("Fehler");

            model.addAttribute("allProjects", projectService.findAllProjects());

            return "redirect:/projectDetails";
        }
        else{

            projectService.saveProject(project);

            return "redirect:/projectDetails";
        }
    }

    /**
     * This method deletes an existing project inside the database
     *
     * An existing project will be deleted. The corresponding contactID will also be deleted so new contacts can get this
     * ID in the future. Once the project is deleted the page will be reloaded to update the contact table.
     *
     * @param project
     * @return redirect:/projects
     */

    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(Project project) {
        projectService.deleteByProjectID(project.getProjectID());
        return "redirect:/projects";
    }

    /**
     * This method exits the projectDetails
     *
     * This method exits the projectDetails page by clicking on a corresponding button bounded with this method. Once clicked
     * the page will be redirected to the project page.
     *
     * @return redirect:/projects
     */
    @RequestMapping(value = "/backProjectDetails", method = RequestMethod.POST)
    public String backProjectDetails() {
        return "redirect:/projects";
    }

    /**
     * This method removes the chosen Contact from the Contacts of the project
     *
     * @return redirect:/projectDetails
     */
    @RequestMapping(value = "/deleteContactFromProject", method = RequestMethod.POST)
    public String deleteContactFromProject(Contact contact, Project project) {
        System.out.println(contact.getContactID());
        Contact contactTemp = new Contact();
        Set<Contact> contactProject = project.getContacts();
        System.out.println(contactProject.size());
        boolean exists = false;
        for(Contact con : contactProject){
            System.out.println(con.getContactID());
            if(con.getContactID() == (contact.getContactID())){
                exists = true;
                contactTemp = con;
                project.setContacts(new HashSet<>());
                contactProject.remove(contactTemp);
                project.setContacts(contactProject);
                projectService.saveProject(project);
                return "redirect:/projectDetails";
            }
            else{
                exists = false;
            }
        }

        System.out.println(contactProject.size());
        return "redirect:/projectDetails";
    }

    /**
     * This method deletes the chosen Contact from the Contacts of the project
     * @param project
     * @return redirect:/projectContactCreator
     */
    @RequestMapping(value ="/addContactsToProject", method = RequestMethod.POST)
    public String addContactsToProject(Project project, Integer projectID) {
        projectID = project.getProjectID();
        return "redirect:/projectContactCreator";
    }

    /**
     * This method checks whether there are tables to display
     *
     * This method checks whether the project has an existing contact. If the count for each of them is higher
     * than 0 it will return the boolean true
     *
     * @param project
     */
    private void checkTables(Project project){
        boolean existingContacts = false;
        if(project.getContacts().size()>0){
            existingContacts = true;
        }
        else{
            existingContacts = false;
        }
    }
}


