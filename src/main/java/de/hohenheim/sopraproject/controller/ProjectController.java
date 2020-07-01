package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {

    private static Project viewProjectsTemp;
    @Autowired
    private ProjectRepository projectRepository;
    private List<Project> allProjects = new LinkedList<>();
    private Set<Project> foundProjects = new HashSet<>();
    public boolean hasError = false;
    private String searchWord;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String institutes(Model model) {
        allProjects = projectRepository.findAll();
        model.addAttribute("allProjects", allProjects);
        model.addAttribute("hasError", hasError);
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
    public String saveProject(@Valid Project project, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
            hasError = true;
        }
        else{
            hasError = false;
            projectRepository.save(project);
        }
        return "redirect:/projects";
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
        model.addAttribute("allProjects", projectRepository.findAll());
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
//    @RequestMapping("/viewProject")
//    public String viewProject(Project project) {
//        ProjectDetailsController.projectID = project.getProjectID();
//        return "redirect:/projectDetails";
//    }

    /**
     * this method search for a project
     * @param searchWord
     * @return "redirect:/projects"
     */
    @RequestMapping(value ="/searchProject", method = RequestMethod.POST)
    public String searchProjects(String searchWord) {
        return "redirect:/projects";
    }
}
