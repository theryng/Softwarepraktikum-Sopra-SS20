package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactHistoryService;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 * Controller for the Editing of a Contact History
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactHistoryEditorController {

    @Autowired
    private ContactHistoryService contactHistoryService;
    @Autowired
    private ContactService contactService;


    /**
     * Main Method which opens the page contactHistoryEditor,
     * adds all necessary Attributes
     * @param model
     * @return contactHistoryEditor
     */
    @GetMapping("/contactHistoryEditor/{contactID}/{historyID}")
    public String contactHistoryEditor(@PathVariable("contactID") Integer id, @PathVariable("historyID") Integer historyID, Model model) {
        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(id);
        ContactHistoryDTO contactHistoryDTO = new ContactHistoryDTO();
        contactHistoryDTO.setOriginalContactID(id+"");
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        return "contacts/contactHistoryEditor";
    }

    /**
     * Saves ContactHistory to the Database
     * Also Resets the Controller for the next use
     * @param
     * @return contactDetails
     */
    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.POST)
    public String savingContactHistory(@ModelAttribute("contactHistoryEditor")ContactHistoryDTO contactHistoryDTO, Model model) {
        ContactHistory contactHistory = contactHistoryDTO.getContactHistory();
        Set<Contact> list = generateList(contactHistoryDTO.getStringChosenIDs());
        for(Contact con : list){
            contactHistory.addContactHistoryContact(con);
        }
        contactHistoryService.saveContacthistory(contactHistory);

        return "redirect:/contactDetails/"+contactHistoryDTO.getOriginalContactID();
    }

    /**
     * Deletes the ContactHistory
     * @param
     * @return contactDetails
     */
    @RequestMapping(value = "/deleteContactHistory", method = RequestMethod.POST)
    public String deleteContactHistory(ContactHistoryDTO contactHistoryDTO) {
        contactHistoryService.deleteByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());

        return "redirect:/contactDetails/"+contactHistoryDTO.getOriginalContactID();
    }

    /**
     * Deletes the Selected Contact from the Contact History
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/deleteContactFromHistory", method = RequestMethod.POST)
    public String deleteFromContactHistory(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO, Model model) {
        Set<Contact> contactList = generateList(contactHistoryDTO.getStringChosenIDs());
        Contact selectedContact = contactService.findByContactID(contactHistoryDTO.getSelectedContact());

        contactList.remove(selectedContact);
        Set<Contact> list = generateList(contactHistoryDTO.getStringChosenIDs());
        for(Contact con : list){
            contactHistoryDTO.getChosenContacts().add(con);
        }

        model.addAttribute("contactHistoryDTO", contactHistoryDTO);

        return "redirect:/contactHistoryEditor/"+contactHistoryDTO.getOriginalContactID();
    }

    /**
     * Adds a Contact to the Contact History
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/addContactToHistory", method = RequestMethod.POST)
    public String addToContactHistory(ContactHistoryDTO contactHistoryDTO) {
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
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/searchContactForHistoryEditor", method = RequestMethod.POST)
    public String searchContactsForHistoryEditor(ContactHistoryDTO contactHistoryDTO) {
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
       /* if(addContact){
            addContact = false;
        }
        else{
            addContact = true;
        }*/
        return "redirect:/contactHistoryEditor";
    }

    public String generateString(Set<Contact> list){
        String string = "";
        for(Contact con : list){
            string = string + con.getContactID() + " ";
        }
        return string;
    }

    public Set<Contact> generateList(String list){
        Set<Integer> foundList = new HashSet<Integer>();
        String[] stringTemp2  = list.split(" ");
        for(String string : stringTemp2){
            foundList.add(Integer.valueOf(string.trim()));
        }

        Set<Contact> foundContacts = new HashSet<Contact>();
        for(Integer integer : foundList){
            foundContacts.add(contactService.findByContactID(integer));
        }
        return foundContacts;
    }
}