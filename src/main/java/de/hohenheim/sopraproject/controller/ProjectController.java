package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.EditingHistory;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import de.hohenheim.sopraproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProjectController {


    @Autowired
    EditingHistoryService editingHistoryService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String projects(Model model) {
        String searchword = "";
        List<Project> allProjects = projectService.findAllProjects();
        boolean showList = false;
        if(allProjects.size()>0){
            showList = true;
        }
        model.addAttribute("showProject", showList);
        model.addAttribute("allProjects", allProjects);
        model.addAttribute("searchWord", searchword);
        model.addAttribute("project", new Project());

        return "projects";
    }

    /**
     * This method saves a new Project
     *
     * This method saves a newly created Project to the database. Once the new Project was saved to the database the page will
     * be reloaded and the table will be updated. The new Project will now show up on the page Projects.
     *
     * @param project
     * @return redirect:/projects
     */
    @RequestMapping(value="/saveProject", method = RequestMethod.POST)
    public String saveProject(@Valid Project project, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("Fehler");

            model.addAttribute("allProjects", projectService.findAllProjects());

            return "projects";
        }
        else{

            projectService.saveProject(project);

            return "redirect:/projects";
        }
    }

    /**
     * This method finds all project.
     *
     * This method finds all existing project and returns them to the user
     *
     * @param model
     * @return contacts
     */
    @RequestMapping("/allProject")
    public String allProject(Model model) {
        model.addAttribute("allProjects", projectService.findAllProjects());
        return "projects";
    }

    /**
     * This method shows the details of a project
     *
     * This method opens up the projectDetails page of a specific project the user wishes to see. Once the user clicks the
     * button that this method is bound to the window will open and allows him to view the details to this project.
     *
     * @param project
     * @return redirect:/projectDetails
     */
    @RequestMapping("/viewProject")
    public String viewProject(Project project, Integer projectID) {
        projectID = project.getProjectID();
        return "redirect:/projectDetails";
    }

    /**
     * this method search for a project
     * @param searchWord
     * @return "redirect:/projects"
     */
    @PostMapping(value ="/searchProject")
    public String searchProjects(@RequestBody @ModelAttribute("allProjects") LinkedList<Project> allProjects, String searchWord, Model model) {
//        ProjectFinder findProject = new ProjectFinder();
//
//        LinkedList<Project> foundProjectsTemp = findProject.findProjects(searchWord, contactService.findAllProjects());
//
//        allProjects = foundProjectsTemp;
//
//        boolean showList = false;
//        if(allProjects.size()>0){
//            showList = true;
//        }
//        model.addAttribute("showList", showList);
//        String searchword = "";
//        model.addAttribute("searchWord", searchword);
//        model.addAttribute("project", new Project());


        return "projects";
    }
}
