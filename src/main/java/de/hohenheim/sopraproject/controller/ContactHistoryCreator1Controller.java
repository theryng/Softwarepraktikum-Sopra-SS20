package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ContactDTO;
import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Controller for the First Step of the Contact History Creator
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactHistoryCreator1Controller {

    @Autowired
    private ContactService contactService;

    /**
     * Main Method of the Contact History Creation.
     * Adds the necessary Attributes and opens the site.
     * @param model
     * @return contactHistoryCreator1
     */
    @GetMapping("/contactHistoryCreator1/{contactID}")
    public String contactHistoryCreator1Controller(@PathVariable("contactID") Integer contactID, Model model) {

        List<Contact> allContacts = contactService.findAllContacts();
        ContactHistoryDTO contactHistoryDTO = new ContactHistoryDTO();
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        String searchWord = "";
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);

        return "contacts/contactHistoryCreator1";
    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @return contactHistoryCreator1
     */
    @RequestMapping(value ="/searchContactForHistory", method = RequestMethod.POST)
    public String searchContacts(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO,  Model model) {

        System.out.println(contactHistoryDTO.getSearchWord());
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContacts = findContact.findContacts(contactHistoryDTO.getSearchWord(), contactService.findAllContacts());
        if(foundContacts.size()>0){
            contactHistoryDTO.setFoundContacts(foundContacts);
            for(Contact con : foundContacts){
                contactHistoryDTO.setStringFoundIDs(contactHistoryDTO.getStringFoundIDs() + con.getContactID() + " ");
            }
            model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        }
        else{
            model.addAttribute("contactHistoryDTO", contactService.findAllContacts());
        }
        return "contacts/contactHistoryCreator1";
    }

    /**
     * Selects a Contact for the Contact History
     * Reloads the page at the End.
     * @param
     * @return contactHistoryCreator1
     */
    @RequestMapping(value = "/chooseContactForHistory", method = RequestMethod.POST)
    public String chooseContactForHistory(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO, Model model) {
        System.out.println("Test");
        System.out.println(contactHistoryDTO.getSelectedContact());
        System.out.println("Test");
        Contact selectedContact = contactService.findByContactID(contactHistoryDTO.getSelectedContact());
        boolean exists = false;
        List<Integer> chosenList = new LinkedList<Integer>();
        List<Integer> foundList = new LinkedList<Integer>();
        String[] stringTemp;
        if(!(contactHistoryDTO.getStringChosenIDs() == "")){
            String string1 = contactHistoryDTO.getStringChosenIDs();
            String[] stringTemp1  = string1.split(" ");
            for(String strings : stringTemp1) {
                chosenList.add(Integer.valueOf(strings));
            }
        }

        String string2 = contactHistoryDTO.getStringFoundIDs();
        String[] stringTemp2  = string2.split(" ");
        for(String string : stringTemp2){
            foundList.add(Integer.valueOf(string.trim()));
        }
        chosenList.add(selectedContact.getContactID());

        for(Integer integer : chosenList){
            contactHistoryDTO.getChosenContacts().add(contactService.findByContactID(integer));
            contactHistoryDTO.setStringChosenIDs(contactHistoryDTO.getStringChosenIDs() + integer + " ");
        }
        for(Integer integer : foundList){
            contactHistoryDTO.getFoundContacts().add(contactService.findByContactID(integer));
        }

        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        return "contacts/contactHistoryCreator1";
    }

    /**
     * Deletes the Contact specified in the HTML page.
     * Finds the chosen Contact and deletes it.
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteChoosenContacts", method = RequestMethod.POST)
    public String deleteChosenContacts(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO, Model model) {
        List<Contact> contacts = contactHistoryDTO.getChosenContacts();
        for(Contact con : contacts){
            if(con.getContactID() == contactHistoryDTO.getSelectedContact()){
                contactHistoryDTO.getChosenContacts().remove(con);
            }
        }
        if(contactHistoryDTO.getChosenContacts().size()<1){
        }
        return "contactHistoryCreator1";
    }

    /**
     * Back Button which returns the user to the contactDetails Site
     * Also calls the resetsController method, to ensure a blank slate for the next Creation process.
     * @return contactDetails
     */
    @RequestMapping(value = "/backContactHistoryCreator1", method = RequestMethod.POST)
    public String backContactHistoryCreator1() {

        return "redirect:/contactDetails";
    }

    /**
     * Function which resets the Controller for the next use
     */
}