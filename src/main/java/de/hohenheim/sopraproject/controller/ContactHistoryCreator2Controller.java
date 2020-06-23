package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
public class ContactHistoryCreator2Controller {

    public static Set<Contact> choosenContacts;

    @Autowired
    private RelationshipRepository relationshipRepository;


    @RequestMapping(value = "/contactHistoryCreator2", method = RequestMethod.GET)
    public String ContactHistoryCreatorController(Model model) {
        model.addAttribute("contactHistory", new ContactHistory());
        System.out.println("In Creator 2");
        return "contacts/contactHistoryCreator2";
    }
    @RequestMapping(value = "/saveFinalContactHistory", method = RequestMethod.POST)
    public String saveContactHistory(ContactHistory contactHistory){
        contactHistory.setContactOfHistory(choosenContacts);

        choosenContacts.clear();

        return "redirect:/contactDetails";
    }

    @RequestMapping(value = "/backContactHistoryCreator2", method = RequestMethod.POST)
    public String backContactHistoryCreator2() {

        return "redirect:/contactHistoryCreator1";
    }
}
