package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;
/**
 * Controller for the Editing of a Contact History
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactHistoryEditorController {

    @Autowired
    private ContactHistoryRepository contacthistoryRepository;
    @Autowired
    private ContactRepository contactRepository;

    private String searchWord;
    private Set<Contact> foundContacts = new HashSet<>();
    private boolean viewTable;
    private boolean addContact;

    /**
     * Main Method which opens the page contactHistoryEditor,
     * adds all necessary Attributes
     * @param model
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/contactHistoryEditor", method = RequestMethod.GET)
    public String contactHistoryEditor(@PathVariable("contactHistoryID") Integer contactHistoryID, Model model) {
        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("addContact", addContact);
        model.addAttribute("viewTable", viewTable);
        model.addAttribute("contactHistory", contacthistoryRepository.findByContactHistoryID(contactHistoryID));
        return "contacts/contactHistoryEditor";
    }

    /**
     * Saves ContactHistory to the Database
     * Also Resets the Controller for the next use
     * @param contactHistoryTemp
     * @return contactDetails
     */
    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.POST)
    public String savingContactHistory(@PathVariable("contactHistoryID") Integer contactHistoryID, ContactHistory contactHistoryTemp) {
        /*ContactHistory contactHistory = contacthistoryRepository.findByContactHistoryID();
        contactHistory.setDate(contactHistoryTemp.getDate());
        contactHistory.setText(contactHistoryTemp.getText());
        contacthistoryRepository.save(contactHistory);
        viewTable = false;
        addContact = false;
        contactHistory = null;
        foundContacts.clear();*/
        return "redirect:/contactDetails";
    }

    /**
     * Deletes the ContactHistory
     * @param contactHistory
     * @return contactDetails
     */
    @RequestMapping(value = "/deleteContactHistory", method = RequestMethod.POST)
    public String deleteContactHistory(ContactHistory contactHistory) {
        contacthistoryRepository.deleteById(contactHistory.getContactHistoryID());
        foundContacts.clear();
        return "redirect:/contactDetails";
    }

    /**
     * Deletes the Selected Contact from the Contact History
     * @param contact
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/deleteContactFromHistory", method = RequestMethod.POST)
    public String deleteFromContactHistory(Contact contact) {
        /*
        Set<Contact> contacts = contactHistory.getContactOfHistory();
        Contact tempContact = new Contact();
        int contactIDA = contact.getContactID();
        for(Contact con : contacts){
            int contactIDB = con.getContactID();
            if(contactIDA == contactIDB){
                tempContact = con;
            }
        }
        try{
            contacts.remove(tempContact);
        } catch (Exception e) {
            System.out.println("Deletion not possible");
        }
*/
        return "redirect:/contactHistoryEditor";
    }

    /**
     * Adds a Contact to the Contact History
     * @param contact
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/addContactToHistory", method = RequestMethod.POST)
    public String addToContactHistory(Contact contact) {
        /*
        Set<Contact> existingContacts = contactHistory.getContactOfHistory();
        Contact addedContact = contactRepository.findByContactID(contact.getContactID());
        boolean exists = false;

        for(Contact con : existingContacts){
            if(con.getContactID().equals(contact.getContactID())){

                exists = true;
            }
        }
        if(!exists){
            existingContacts.add(addedContact);
        }
        else{
            System.out.println("Adding of Contact stopped, as it already exists");
        }
*/
        return "redirect:/contactHistoryEditor";
    }

    /**
     * Searches the Contacts for a Contact which can be added to the History
     * Uses a searchword and calls the findContacts Method of the ContactFinder
     * @param searchWord
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/searchContactForHistoryEditor", method = RequestMethod.POST)
    public String searchContactsForHistoryEditor(String searchWord) {
        ContactFinder findContact = new ContactFinder();
        /*Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTable = true;
        }
        else{
            foundContacts.clear();
            viewTable = false;
        }*/
        return "redirect:/contactHistoryEditor";
    }

    /**
     *  Enables the Viewing of the addContact Table,
     *  if its already seen it hides it.
     * @param searchWord
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/enableAddContacts", method = RequestMethod.POST)
    public String enableAddContacts(String searchWord) {
        if(addContact){
            addContact = false;
        }
        else{
            addContact = true;
        }
        return "redirect:/contactHistoryEditor";
    }

    /**
     * Back Button which returns to contactDetails
     * @return contactDetails
     */
    @RequestMapping(value = "/backContactHistoryEditor", method = RequestMethod.POST)
    public String backContactHistoryEditor() {
        return "redirect:/contactDetails";
    }
}