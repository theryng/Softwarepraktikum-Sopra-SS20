package de.hohenheim.sopraproject.controller.contacts;

import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import de.hohenheim.sopraproject.service.ContactHistoryService;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 * Controller for the second Step of the Contact History Creator
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactHistoryCreator2Controller {

    @Autowired
    private ContactHistoryService contactHistoryService;

    @Autowired
    private ContactService contactService;


    /**
     * Main Method which opens the site contactHistoryCreator2,
     * also adds the necessary Attributes
     * @param model
     * @return contactHistoryCreator2
     */
    @GetMapping("/contactHistoryCreator2/{contactID}")
    public String ContactHistoryCreatorController(@PathVariable("contactID") Integer contactID, @ModelAttribute("chosenContacts") LinkedList chosenContacts, Model model, @ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO) {
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        return "contacts/contactHistoryCreator2";
    }

    /**
     * Save method, saves the ContactHistory in the Database and returns the User to the ContactDetails page
     * @param
     * @return contactDetails
     */
    @RequestMapping(value = "/saveFinalContactHistory", method = RequestMethod.POST)
    public String saveContactHistory(@ModelAttribute("contactHistoryDTO") ContactHistoryDTO contactHistoryDTO, BindingResult result){
        if(result.hasErrors()){
            return "contactHistoryCreator2";
        }
        else{
            ContactHistory contactHistory = new ContactHistory();

            contactHistory.setText(contactHistoryDTO.getContactHistory().getText());
            contactHistory.setDate(contactHistoryDTO.getContactHistory().getDate());

            List<Contact> contacts = generateList(contactHistoryDTO.getStringChosenIDs());
            for(Contact con : contacts){
                contactHistory.addContactHistoryContact(con);
            }
            contactHistory.addContactHistoryContact(contactService.findByContactID(Integer.valueOf(contactHistoryDTO.getOriginalContactID())));

            Contact contact = contactService.findByContactID(Integer.valueOf(contactHistoryDTO.getOriginalContactID()));
            for(Contact con : contactHistory.getContactOfHistory()){
                con.setLastContact(contactHistory.getDate());
                contactService.saveContact(con);
            }
            contactHistoryService.saveContacthistory(contactHistory);
            return "redirect:/contactDetails/"+contactHistoryDTO.getOriginalContactID();
        }
    }

    /**
     * Help Method which creates a List of Contacts out of a String of IDs
     * @param list
     * @return
     */
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
}