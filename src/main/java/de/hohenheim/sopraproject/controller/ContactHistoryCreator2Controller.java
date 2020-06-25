package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;
/**
 * Controller for the second Step of the Contact History Creator
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactHistoryCreator2Controller {

    public static Contact originalContact;

    public static Set<Contact> choosenContacts;

    @Autowired
    private ContactHistoryRepository contactHistoryRepository;

    private static boolean hasError = false;

    /**
     * Main Method which opens the site contactHistoryCreator2,
     * also adds the necessary Attributes
     * @param model
     * @return contactHistoryCreator2
     */
    @RequestMapping(value = "/contactHistoryCreator2", method = RequestMethod.GET)
    public String ContactHistoryCreatorController(Model model) {
        model.addAttribute("hasError", hasError);
        model.addAttribute("contactHistory", new ContactHistory());
        System.out.println("In Creator 2");
        return "contacts/contactHistoryCreator2";
    }

    /**
     * Save method, saves the ContactHistory in the Database and returns the User to the ContactDetails page
     * @param contactHistory
     * @return contactDetails
     */
    @RequestMapping(value = "/saveFinalContactHistory", method = RequestMethod.POST)
    public String saveContactHistory(ContactHistory contactHistory, BindingResult result){
        if(result.hasErrors()){
            hasError = true;
            return "redirect:/contactHistoryCreator2";
        }
        else{
            hasError = false;
            System.out.println("Speichern");
            System.out.println(choosenContacts.size());
            System.out.println(contactHistory.getDate());
            System.out.println(contactHistory.getText());
            contactHistory.setContactOfHistory(choosenContacts);
            contactHistory.getContactOfHistory().add(originalContact);
            contactHistoryRepository.save(contactHistory);
            choosenContacts.clear();
            ContactHistoryCreator1Controller.resetController();
            return "redirect:/contactDetails";
        }
    }

    /**
     * Return Method, which returns the User to the first part of the Creation process
     * @return contactHistoryCreator1
     */
    @RequestMapping(value = "/backContactHistoryCreator2", method = RequestMethod.POST)
    public String backContactHistoryCreator2() {
        return "redirect:/contactHistoryCreator1";
    }
}