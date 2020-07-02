package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller for the First Step of the Contact History Creator
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class InstituteContactCreatorController {

    private Institute institute;
    public static int insituteID;
    private ContactHistory contactHistoryTemp;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private ContactRepository contactRepository;
    private String searchWord;
    private static Set<Contact> foundContacts = new HashSet<>();
    private static Set<Contact> chosenContacts = new HashSet<>();
    private static boolean viewTableHistories;
    private static boolean viewChoosenTable;

    /**
     * Main Method of the Institute Contact Creator.
     * Adds the necessary Attributes and opens the site.
     * @param model
     * @return instituteContactCreator
     */
    @RequestMapping(value = "/instituteContactCreator", method = RequestMethod.GET)
    public String instituteContactCreator(Model model) {
        institute = instituteRepository.findByInstituteID(insituteID);
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
        return "institutes/instituteContactCreator";
    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return instituteContactCreator
     */
    @RequestMapping(value ="/searchContactForInstituteCreator", method = RequestMethod.POST)
    public String searchContacts(String searchWord) {
        ContactFinder findContact = new ContactFinder();
        /*
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTableHistories = true;
        }
        else{
            foundContacts.clear();
            viewTableHistories = false;
        }
        */
        return "redirect:/instituteContactCreator";
    }

    /**
     * Selects a Contact for the Institute.
     * Reloads the page at the End.
     * @param contact
     * @return instituteContactCreator
     */
    @RequestMapping(value = "/chooseContactForInstitute", method = RequestMethod.POST)
    public String chooseContactForInstitute(Contact contact) {
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
        return "redirect:/instituteContactCreator";
    }

    /**
     * Submits the chosen Contacts to the Institute
     * @return instituteDetails
     */
    @RequestMapping(value = "/submitContactsForInstitute", method = RequestMethod.POST)
    public String submitContactsForInstitute() {
        InstituteDetailsController.institute.getContacts().addAll(chosenContacts);
        instituteRepository.save(InstituteDetailsController.institute);
        resetController();
        return "redirect:/instituteDetails";
    }

    /**
     * Deletes the Contact specified in the HTML page.
     * Finds the chosen Contact and deletes it.
     * @param contact
     * @return
     */
    @RequestMapping(value = "/deleteContactsForInstitute", method = RequestMethod.POST)
    public String deleteChosenContacts(Contact contact) {

        for(Contact con : chosenContacts){
            if(con.getContactID() == contact.getContactID()){
                chosenContacts.remove(con);
            }
        }
        if(chosenContacts.size()<1){
            viewChoosenTable = false;
        }
        return "redirect:/instituteContactCreator";
    }

    /**
     * Back Button which returns the user to the instituteDetails Site
     * Also calls the resetsController method, to ensure a blank slate for the next Creation process.
     * @return contactDetails
     */
    @RequestMapping(value = "/backInstituteContactCreator", method = RequestMethod.POST)
    public String backInstituteContactCreator() {
        try {
            resetController();
        } finally {
            System.out.println("Nothing to clear");
        }
        return "redirect:/instituteDetails";
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