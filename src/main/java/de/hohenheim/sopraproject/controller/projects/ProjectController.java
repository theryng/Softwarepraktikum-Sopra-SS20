package de.hohenheim.sopraproject.controller.projects;

import de.hohenheim.sopraproject.dto.ProjectDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import de.hohenheim.sopraproject.service.ProjectService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    private EditingHistoryService editingHistoryService;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private TagsService tagsService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String projects(Model model) {
        List<Project> allProjects = projectService.findAllProjects();
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAllProjects(allProjects);
        projectDTO.setProject(new Project());
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("searchWord", "");
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
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
    public String saveProject(@Valid ProjectDTO projectDTO, BindingResult result, Principal principal){
        if(result.hasErrors()){
            System.out.println("Fehler");
        }
        else{
            projectService.saveProject(projectDTO.getProject());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();


            editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Projekt: " + projectDTO.getProject().getName(), dateFormat.format(date)));
        }
        return "redirect:/projects";
    }
    /**
     *  Method which can be used to search for a certain Project.
     *  Calls the Contact Finder, and uses a searchWord to find a Project.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchProject", method = RequestMethod.POST)
    public String searchProjects(String searchWord, Model model) {
        List<Project> allProjects;
        ContactFinder findContact = new ContactFinder();

        LinkedList<Project> foundProjectTemp = findContact.findProjects(searchWord, projectService.findAllProjects());

        allProjects = foundProjectTemp;

        boolean showList = false;
        if(allProjects.size()>0){
            showList = true;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAllProjects(allProjects);
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("searchWord", "");
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        return "projects";
    }

    @PostMapping(value ="/sortByTagProjects")
    public String sortByTag(Tags tag, Model model) {
        Tags tags = tagsService.findByTagID(tag.getTagsID());
        List<Project> allProjects = projectService.findAllProjects();
        List<Project> foundProject = new LinkedList<Project>();
        for(Project project : allProjects){
            if(project.getTags().contains(tags)){
                foundProject.add(project);
            }
        }
        allProjects = foundProject;
        boolean showList = false;
        if(allProjects.size()>0){
            showList = true;
        }
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setAllProjects(allProjects);
        projectDTO.setProject(new Project());
        model.addAttribute("projectDTO", projectDTO);
        model.addAttribute("searchWord", "");
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        return "projects";
    }
}

