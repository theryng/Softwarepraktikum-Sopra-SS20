package de.hohenheim.sopraproject.controller.projects;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactService;
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
        System.out.println("Testing the stuff " + projectID);
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = projectService.findByProjectID(projectID);
        System.out.println("Anzahl Tags: " + project.getTags().size());
        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(projectID);
        projectDTO.setProject(project);
        projectDTO.setProjectID(projectID);
        model.addAttribute("allContacts", project.getContacts());
        model.addAttribute("project", project);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("viewTable", true);
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
    public String savingProject(@Valid ProjectDTO projectDTO, BindingResult result, Model model) {
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
            return "redirect:/projectDetails/"+project.getProjectID();
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


    @GetMapping("/deleteProjectTag")
    public String deleteProjectTag(TagsDTO tagsDTO) {
        List<Tags> tags = projectService.findByProjectID(tagsDTO.getOriginalID()).getTags();
        System.out.println("Number of Tags 1" + tags.size());
        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){
                System.out.println("remove");
                removeTag = tag;
            }
        }
        tags.remove(removeTag);

        System.out.println("Number of Tags 2" + tags.size());
        Project project = projectService.findByProjectID(tagsDTO.getOriginalID());
        project.setTags(tags);
        System.out.println(project.getTags().size());
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