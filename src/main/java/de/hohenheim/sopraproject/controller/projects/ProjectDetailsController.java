package de.hohenheim.sopraproject.controller.projects;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import de.hohenheim.sopraproject.service.ProjectService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page ProjectDetails
 *
 * It helps with various method to save new projects, delete existing ones, open up the ProjectContactCreator.
 *
 */
@Controller
public class ProjectDetailsController {


    @Autowired
    private EditingHistoryService editingHistoryService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Main method for Viewing of project Details Site, adds necessary Attributes
     * @param model
     * @return projectDetails
     */
    @RequestMapping(value = "/projectDetails/{projectID}", method = RequestMethod.GET)
    public String projectDetails(@PathVariable("projectID") Integer projectID, Model model) {

        ProjectDTO projectDTO = new ProjectDTO();
        Project project = projectService.findByProjectID(projectID);

        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(projectID);
        if(project.getTags().size()>0){
            model.addAttribute("viewTags", true);
        }
        else{
            model.addAttribute("viewTags", false);
        }
        projectDTO.setProject(project);
        projectDTO.setProjectID(projectID);
        model.addAttribute("allContacts", project.getContacts());
        model.addAttribute("project", project);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", checkTables(project));
        model.addAttribute("tagDTO", tagsDTO);
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
    public String savingProject(@Valid ProjectDTO projectDTO, BindingResult result, Model model, Principal principal) {
        Project project = projectDTO.getProject();
        project.setProjectID(projectDTO.getProjectID());
        if(result.hasErrors()){
            return "redirect:/projectDetails/"+project.getProjectID();
        }
        else{
            if(!projectService.findByProjectID(project.getProjectID()).equals(project)){
                projectService.saveProject(project);
            }
            projectDTO.setProject(project);
            model.addAttribute("projectDTO", projectDTO);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();


            editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Projektdetails: " + projectDTO.getProject().getName(), dateFormat.format(date)));

            return "redirect:/projectDetails/"+project.getProjectID();
        }
    }

    @RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
    public String deleteProject(ProjectDTO projectDTO) {
        projectService.deleteByProjectID(projectDTO.getProjectID());
        return "redirect:/projects";
    }

    @RequestMapping(value = "/deleteContactFromProject", method = RequestMethod.POST)
    public String deleteContactFromProject(ProjectDTO projectDTO, Model model, Principal principal) {
        Project project = projectService.findByProjectID(projectDTO.getProjectID());
        Set<Contact> contacts = project.getContacts();

        Contact deleteContact = contactService.findByContactID(projectDTO.getContactTempID());
        contacts.remove(deleteContact);

        project.setContacts(contacts);
        projectService.saveProject(project);
        projectDTO.setProject(project);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", checkTables(project));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


        editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Projekt Mitarbeiter: " + projectDTO.getProject().getName(), dateFormat.format(date)));


        return "redirect:/projectDetails/"+project.getProjectID();
    }


    @GetMapping("/deleteProjectTag")
    public String deleteProjectTag(TagsDTO tagsDTO) {
        List<Tags> tags = projectService.findByProjectID(tagsDTO.getOriginalID()).getTags();

        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){

                removeTag = tag;
            }
        }
        tags.remove(removeTag);


        Project project = projectService.findByProjectID(tagsDTO.getOriginalID());
        project.setTags(tags);

        projectService.saveProject(project);
        Tags tag = tagsService.findByTagID(removeTag.getTagsID());
        tag.getProjects().remove(project);
        tagsService.saveTags(tag);
        return "redirect:/projectDetails/"+project.getProjectID();
    }

    private boolean checkTables(Project project){
        if(project.getContacts().size()>0){
            return true;
        }
        return false;
    }
}
