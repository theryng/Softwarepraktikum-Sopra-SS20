package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
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
public class ContactHistoryCreator1Controller {

    public static Contact originalContact;
    private ContactHistory contactHistoryTemp;
    @Autowired
    private ContactRepository contactRepository;
    private String searchWord;
    private static Set<Contact> foundContacts = new HashSet<>();
    private static Set<Contact> chosenContacts = new HashSet<>();
    private static boolean viewTableHistories;
    private static boolean viewChoosenTable;

    /**
     * Main Method of the Contact History Creation.
     * Adds the necessary Attributes and opens the site.
     * @param model
     * @return contactHistoryCreator1
     */
    @RequestMapping(value = "/contactHistoryCreator1", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
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
        return "contacts/contactHistoryCreator1";
    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchContactForHistory", method = RequestMethod.POST)
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
        return "redirect:/contactHistoryCreator1";
    }

    /**
     * Selects a Contact for the Contact History
     * Reloads the page at the End.
     * @param contact
     * @return contactHistoryCreator1
     */
    @RequestMapping(value = "/chooseContactForHistory", method = RequestMethod.POST)
    public String setContactB(Contact contact) {
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
        return "redirect:/contactHistoryCreator1";
    }

    /**
     * Submits the chosen Contacts for the next Step of the
     * Creation process.
     * @return contactHistoryCreator2
     */
    @RequestMapping(value = "/submitChosenContacts", method = RequestMethod.POST)
    public String submitChosenContacts() {
        ContactHistoryCreator2Controller.choosenContacts=chosenContacts;
        return "redirect:/contactHistoryCreator2";
    }

    /**
     * Deletes the Contact specified in the HTML page.
     * Finds the chosen Contact and deletes it.
     * @param contact
     * @return
     */
    @RequestMapping(value = "/deleteChoosenContacts", method = RequestMethod.POST)
    public String deleteChosenContacts(Contact contact) {

        for(Contact con : chosenContacts){
            if(con.getContactID() == contact.getContactID()){
                chosenContacts.remove(con);
            }
        }
        if(chosenContacts.size()<1){
            viewChoosenTable = false;
        }
        return "redirect:/contactHistoryCreator1";
    }

    /**
     * Back Button which returns the user to the contactDetails Site
     * Also calls the resetsController method, to ensure a blank slate for the next Creation process.
     * @return contactDetails
     */
    @RequestMapping(value = "/backContactHistoryCreator1", method = RequestMethod.POST)
    public String backContactHistoryCreator1() {
        try {
            resetController();
        } finally {
            System.out.println("Nothing to clear");
        }
        return "redirect:/contactDetails";
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
