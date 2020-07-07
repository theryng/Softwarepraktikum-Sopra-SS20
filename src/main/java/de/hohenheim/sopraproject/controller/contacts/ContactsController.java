package de.hohenheim.sopraproject.controller.contacts;

import de.hohenheim.sopraproject.dto.SearchDTO;
import de.hohenheim.sopraproject.entity.Address;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.EditingHistory;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This controller contains the methods to create a new contact
 *
 * This controller contains all important methods to create a new contact and saving it inside the database.
 * It allows to get all informations about a contact, to save a newly created contact and to look up all the details
 * about a certain contact.
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactsController {

    @Autowired
    EditingHistoryService editingHistoryService;
    @Autowired
    private ContactService contactService;

    /**
     * This method gets all the information about a contact
     *
     * This method gives out all the information about the contacts stored inside the database
     *
     * @param model
     * @return contacts
     */
    @RequestMapping(value ="/contacts", method = RequestMethod.GET)
    public String contacts(Model model) {
        String searchword = "";
        List<Contact> allContacts = contactService.findAllContacts();
        boolean showList = false;
        if(allContacts.size()>0){
            showList = true;
        }
        model.addAttribute("showList", showList);
        model.addAttribute("allContacts", allContacts);
        model.addAttribute("searchWord", new SearchDTO());
        model.addAttribute("contact", new Contact());

        return "contacts";
    }

    /**
     * This method saves a new contact
     *
     * This method saves a newly created contact to the database. It also merges the temporary attributes of ZipCode, City,
     * Street and houseNumber to one single attribute called address. Once the new contact was saved to the database the page will
     * be reloaded and the table will be updated. The new contact will no show up on the page contacts.
     *
     * @param contact
     * @return redirect:/contacts
     */
    @RequestMapping(value="/saveContact", method = RequestMethod.POST)
    public String saveContact(@Valid Contact contact, BindingResult result, Principal principal, Model model){
        if(result.hasErrors()){
            System.out.println("Fehler");

            model.addAttribute("allContacts", contactService.findAllContacts());

            return "contacts";
        }
        else{
            contactService.saveContact(contact);


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            System.out.println(dateFormat.format(date));

            editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Kontakt: " + contact.getFirstname() + " " + contact.getLastname(), dateFormat.format(date)));
            return "redirect:/contacts";
        }
    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @param searchDTO
     * @return contactHistoryCreator1
     */
    @PostMapping(value ="/searchContact")
    public String searchContacts(@RequestBody @ModelAttribute("allContacts") LinkedList<Contact> allContacts, SearchDTO searchDTO, Model model) {
        ContactFinder findContact = new ContactFinder();

        LinkedList<Contact> foundContactsTemp = findContact.findContacts(searchDTO.getSearchWord(), contactService.findAllContacts(), "Name");

        allContacts = foundContactsTemp;

        boolean showList = false;
        if(allContacts.size()>0){
            showList = true;
        }
        model.addAttribute("showList", showList);
        String searchword = "";
        model.addAttribute("allContacts", allContacts);
        model.addAttribute("searchWord", searchword);
        model.addAttribute("contact", new Contact());

        return "contacts";
    }
}