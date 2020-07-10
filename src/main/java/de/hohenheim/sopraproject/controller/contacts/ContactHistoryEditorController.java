package de.hohenheim.sopraproject.controller.contacts;

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

        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(historyID);
        ContactHistoryDTO contactHistoryDTO = new ContactHistoryDTO();
        contactHistoryDTO.setOriginalContactID(id+"");
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());
        boolean checkConnection = checkConnection(contactHistory);
        boolean projectOrEvent = false;
        if(!(contactHistory.getProject() ==null)){
            contactHistoryDTO.setConnectedID(contactHistory.getProject().getProjectID());
            projectOrEvent = true;
        }
        if(!(contactHistory.getEvent() ==null)){
            contactHistoryDTO.setConnectedID(contactHistory.getEvent().getEventID());
            projectOrEvent = false;
        }
        model.addAttribute("projectOrEvent", projectOrEvent);
        model.addAttribute("viewConnection", checkConnection);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        model.addAttribute("allContacts", contactService.findAllContacts());
        return "contacts/contactHistoryEditor";
    }

    /**
     * Saves ContactHistory to the Database
     * Also Resets the Controller for the next use
     * @param
     * @return contactDetails
     */
    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.GET)
    public String savingContactHistory(@ModelAttribute("contactHistoryEditor")ContactHistoryDTO contactHistoryDTO, Model model) {
        ContactHistory contactHistory = contactHistoryDTO.getContactHistory();
        System.out.println(contactHistoryDTO.getContactHistory().getText());
        System.out.println(contactHistory.getDate());
        System.out.println(contactHistory.getContactHistoryID());
        Set<Contact> list = generateSet(contactHistoryDTO.getStringChosenIDs());
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

        return "redirect:/contactDetails"+contactHistoryDTO.getOriginalContactID();
    }

    /**
     * Deletes the Selected Contact from the Contact History
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/deleteContactFromHistory", method = RequestMethod.POST)
    public String deleteFromContactHistory(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO, Model model) {
        System.out.println("Delete Contact: "+contactHistoryDTO.getSelectedContact());
        String string = contactHistoryDTO.getStringChosenIDs();

        Set<Contact> contactList = generateSet(string);

        contactList.remove(contactService.findByContactID(contactHistoryDTO.getSelectedContact()));

        List<Contact> chosenContacts = new LinkedList<Contact>();
        for(Contact con : contactList){
            System.out.println(con.getContactID()+"");
            chosenContacts.add(con);
        }
        System.out.println(generateString(contactList));
        contactHistoryDTO.setStringChosenIDs(generateString(contactList));
        contactHistoryDTO.setChosenContacts(chosenContacts);
        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());
        contactHistory.setContactOfHistory(contactList);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());
        contactHistoryService.saveContacthistory(contactHistory);
        boolean checkConnection = checkConnection(contactHistory);
        model.addAttribute("viewConnection", checkConnection);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);

        return "contactHistoryEditor";
    }

    /**
     * Adds a Contact to the Contact History
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value = "/addContactToHistory", method = RequestMethod.POST)
    public String addToContactHistory(ContactHistoryDTO contactHistoryDTO, Model model) {

        Set<Contact> existingContacts = generateSet(contactHistoryDTO.getStringChosenIDs());
        Contact addedContact = contactService.findByContactID(contactHistoryDTO.getSelectedContact());
        boolean exists = false;

        for(Contact con : existingContacts){
            if(con.getContactID().equals(contactHistoryDTO.getSelectedContact())){
                exists = true;
            }
        }
        if(!exists){
            existingContacts.add(addedContact);
        }
        else{
            System.out.println("Adding of Contact stopped, as it already exists");
        }
        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());
        contactHistory.setContactOfHistory(existingContacts);
        contactHistoryService.saveContacthistory(contactHistory);
        contactHistoryDTO.setOriginalContactID(contactHistoryDTO.getOriginalContactID());
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());

        boolean checkConnection = checkConnection(contactHistory);
        model.addAttribute("viewConnection", checkConnection);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        model.addAttribute("addContact", false);
        model.addAttribute("viewTable", false);

        return "contacts/contactHistoryEditor";
    }

    /**
     * Searches the Contacts for a Contact which can be added to the History
     * Uses a searchword and calls the findContacts Method of the ContactFinder
     * @param
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/searchContactForHistoryEditor", method = RequestMethod.POST)
    public String searchContactsForHistoryEditor(ContactHistoryDTO contactHistoryDTO, Model model) {
        System.out.println(contactHistoryDTO.getStringFoundIDs());

        if(!(contactHistoryDTO.getStringFoundIDs().equals(""))){
            contactHistoryDTO.setChosenContacts(generateList(contactHistoryDTO.getStringFoundIDs()));
        }
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContacts = findContact.findContacts(contactHistoryDTO.getSearchWord(), contactService.findAllContacts());

        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());
        contactHistoryDTO.setOriginalContactID(contactHistoryDTO.getOriginalContactID());
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());
        System.out.println("Anzahl Suchergebnisse: "+foundContacts.size());
        if(foundContacts.size()>0){
            String string = "";
            for(Contact con : foundContacts){
                string = string + con.getContactID() + " ";
            }
            contactHistoryDTO.setStringFoundIDs(string);
            model.addAttribute("contactHistoryDTO", contactHistoryDTO);
            model.addAttribute("allContacts", foundContacts);
            model.addAttribute("addContact", true);
            model.addAttribute("viewTable", true);
        }
        else{
            model.addAttribute("contactHistoryDTO", contactHistoryDTO);
            model.addAttribute("allContacts", contactService.findAllContacts());
            model.addAttribute("addContact", true);
            model.addAttribute("viewTable", true);
        }
        model.addAttribute("viewConnection", checkConnection(contactHistory));
        System.out.println("Die gefunden Kontakte sind: "+contactHistoryDTO.getFoundContacts());
        return "contacts/contactHistoryEditor";
    }

    /**
     *  Enables the Viewing of the addContact Table,
     *  if its already seen it hides it.
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/enableAddContacts", method = RequestMethod.POST)
    public String enableAddContacts(ContactHistoryDTO contactHistoryDTO, Model model) {

        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());
        contactHistoryDTO.setOriginalContactID(contactHistoryDTO.getOriginalContactID());
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());
        boolean checkConnection = checkConnection(contactHistory);
        model.addAttribute("viewConnection", checkConnection);
        model.addAttribute("viewConnection", checkConnection(contactHistory));
        model.addAttribute("addContact", true);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        return "contacts/contactHistoryEditor";
    }
    /**
     *  Enables the Viewing of the addContact Table,
     *  if its already seen it hides it.
     * @return contactHistoryEditor
     */
    @RequestMapping(value ="/removeConnection", method = RequestMethod.POST)
    public String removeConnection(ContactHistoryDTO contactHistoryDTO, Model model) {

        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getContactHistory().getContactHistoryID());
        contactHistory.setProject(null);
        contactHistory.setEvent(null);
        contactHistoryService.saveContacthistory(contactHistory);
        contactHistoryDTO.setOriginalContactID(contactHistoryDTO.getOriginalContactID());
        contactHistoryDTO.setFoundContacts(contactService.findAllContacts());
        contactHistoryDTO.setStringChosenIDs(generateString(contactHistory.getContactOfHistory()));
        contactHistoryDTO.setContactHistory(contactHistory);
        contactHistoryDTO.setOriginalContactHistoryID(contactHistory.getContactHistoryID());
        boolean checkConnection = checkConnection(contactHistory);
        model.addAttribute("viewConnection", checkConnection);
        model.addAttribute("addContact", true);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        return "contacts/contactHistoryEditor";
    }

    public String generateString(Set<Contact> list){
        String string = "";
        for(Contact con : list){
            string = string + con.getContactID() + " ";
        }
        return string;
    }

    public Set<Contact> generateSet(String list){
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

    public List<Contact> generateList(String list){
        List<Integer> foundList = new LinkedList<Integer>();
        String[] stringTemp2  = list.split(" ");
        for(String string : stringTemp2){
            foundList.add(Integer.valueOf(string.trim()));
        }

        List<Contact> foundContacts = new LinkedList<Contact>();
        for(Integer integer : foundList){
            foundContacts.add(contactService.findByContactID(integer));
        }
        return foundContacts;
    }

    public boolean checkConnection(ContactHistory contactHistory){
        System.out.println("Checking 1");
        if( contactHistory.getEvent()==null && contactHistory.getProject()==null){
            System.out.println("Checking 2");
            return false;
        }

        System.out.println("Checking 4");
        return true;
    }
}