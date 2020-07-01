package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ProjectRepository;
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
 * @author Mark Wagner but oriented by work of Lukas Januschke
 */
@Controller
public class ProjectDetailsController {

    public static Integer projectID;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContactRepository contactRepository;

    public static Project project;


    private boolean existingContacts = false;
    public boolean hasError = false;

    /**
     * Main method for Viewing of project details Site, adds necessary attributes
     * @param model
     * @return projectDetails
     */
    @RequestMapping(value = "/projectDetails", method = RequestMethod.GET)
    public String projectDetails(Model model) {
        project = projectRepository.findByProjectID(projectID);
        checkTables(project);
        model.addAttribute("project", project);
        model.addAttribute("hasError", hasError);
        model.addAttribute("projects", new Project());
        model.addAttribute("existingContacts", existingContacts);
        model.addAttribute("tempContact", new Contact());

        hasError = false;
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
    public String savingProjects(@Valid Project project, BindingResult result) {
        if(result.hasErrors()){
            hasError = true;
            return "redirect:/projectDetails";
        }
        else{
            hasError = false;
            project.setProjectID(projectID);
            if(!projectRepository.findByProjectID(project.getProjectID()).equals(project)){
                projectRepository.save(project);
            }
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
        projectRepository.deleteById(project.getProjectID());
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
        projectID = null;
        return "redirect:/projects";
    }

    /**
     * This method removes the chosen Contact from the Contacts of the project
     *
     * @return redirect:/projectDetails
     */
    @RequestMapping(value = "/deleteContactFromProject", method = RequestMethod.POST)
    public String deleteContactFromProject(Contact contact) {
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
                projectRepository.save(project);
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
    public String addContactsToProject(Project project) {
        ProjectContactCreatorController.projectID = project.getProjectID();
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
        if(project.getContacts().size()>0){
            existingContacts = true;
        }
        else{
            existingContacts = false;
        }
    }
}
