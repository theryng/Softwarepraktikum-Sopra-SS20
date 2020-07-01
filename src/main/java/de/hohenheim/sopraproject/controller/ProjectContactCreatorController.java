package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Project;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ProjectRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller to add contacts from the database to a project
 */
@Controller
public class ProjectContactCreatorController {

    private Project project;
    public static int projectID;
    private ContactHistory contactHistoryTemp;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ContactRepository contactRepository;
    private String searchWord;
    private static Set<Contact> foundContacts = new HashSet<>();
    private static Set<Contact> chosenContacts = new HashSet<>();
    private static boolean viewTableHistories;
    private static boolean viewChoosenTable;

    /**
     * Main Method of the project contact creator.
     * Adds the necessary Attributes and opens the site.
     * @param model
     * @return projectContactCreator
     */
    @RequestMapping(value = "/projectContactCreator", method = RequestMethod.GET)
    public String projectContactCreator(Model model) {
        project = projectRepository.findByProjectID(projectID);
        contactHistoryTemp = new ContactHistory();
        contactHistoryTemp.setContactOfHistory(foundContacts);
        model.addAttribute("foundContacts", contactHistoryTemp);
        model.addAttribute("chosenContacts", chosenContacts);
        model.addAttribute("viewTableHistories", viewTableHistories);
        model.addAttribute("viewChosenTable", viewChoosenTable);
        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);
        return "projects/projectContactCreator";
    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return projectContactCreator
     */
    @RequestMapping(value ="/searchContactForProjectCreator", method = RequestMethod.POST)
    public String searchContacts(String searchWord) {
        ContactFinder findContact = new ContactFinder();
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTableHistories = true;
        }
        else{
            foundContacts.clear();
            viewTableHistories = false;
        }
        return "redirect:/projectContactCreator";
    }

    /**
     * Selects a Contact for the project.
     * Reloads the page at the End.
     * @param contact
     * @return projectContactCreator
     */
    @RequestMapping(value = "/chooseContactForProject", method = RequestMethod.POST)
    public String chooseContactForProject(Contact contact) {
        Contact selectedContact = contactRepository.findByContactID(contact.getContactID());
        boolean exists = false;
        for(Contact con : chosenContacts){
            if(con.getContactID().equals(selectedContact.getContactID())){
                exists = true;
            }
        }
        if(!exists){
            chosenContacts.add(selectedContact);
            viewChoosenTable = true;
        }
        return "redirect:/projectContactCreator";
    }

    /**
     * Submits the chosen contacts to the project
     * @return projectDetails
     */
    @RequestMapping(value = "/submitContactsForProject", method = RequestMethod.POST)
    public String submitContactsForProject() {
        ProjectDetailsController.project.getContacts().addAll(chosenContacts);
        projectRepository.save(ProjectDetailsController.project);
        resetController();
        return "redirect:/projectDetails";
    }

    /**
     * Deletes the Contact specified in the HTML page.
     * Finds the chosen Contact and deletes it.
     * @param contact
     * @return projectContactCreator
     */
    @RequestMapping(value = "/deleteContactsForProject", method = RequestMethod.POST)
    public String deleteChosenContacts(Contact contact) {

        for(Contact con : chosenContacts){
            if(con.getContactID() == contact.getContactID()){
                chosenContacts.remove(con);
            }
        }
        if(chosenContacts.size()<1){
            viewChoosenTable = false;
        }
        return "redirect:/projectContactCreator";
    }

    /**
     * Back Button which returns the user to the projectDetails Site
     * Also calls the resetsController method, to ensure a blank slate for the next Creation process.
     * @return projectDetails
     */
    @RequestMapping(value = "/backProjectContactCreator", method = RequestMethod.POST)
    public String backProjectContactCreator() {
        try {
            resetController();
        } finally {
            System.out.println("Nothing to clear");
        }
        return "redirect:/projectDetails";
    }

    /**
     * Function which resets the Controller for the next use
     */
    public static void resetController(){
        if(!(foundContacts.size()<1)){
            foundContacts.clear();
        }
        if(!(chosenContacts.size()<1)){
            chosenContacts.clear();
        }
        viewTableHistories = false;
        viewChoosenTable = false;
    }
}
