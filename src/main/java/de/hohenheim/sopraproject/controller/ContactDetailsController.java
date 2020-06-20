package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Contacthistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContacthistoryRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ContactDetailsController {

    @Autowired
    public static Integer contactID;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    private String searchWord;

    private Set<Contact> foundContacts = new HashSet<>();

    private Contact contactB;

    @RequestMapping(value = "/contactDetails", method = RequestMethod.GET)
    public String contactDetails(Model model) {
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contactRepository.findByContactID(contactID));
        model.addAttribute("viewedHistory", new Contacthistory());
        System.out.println("Test");

        if(foundContacts.size()>0){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);

        return "contactDetails";
    }
    @RequestMapping(value = "/savingContact", method = RequestMethod.POST)
    public String contactDetails(Contact contact) {
        contact.setContactID(contactID);
        contactRepository.save(contact);
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public String deleteDetails(Contact contact) {
        contactRepository.deleteById(contact.getContactID());
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping("/openEditContactHistory")
    public String editContactHistory(Contacthistory viewedHistory) {
        System.out.println("View History");
        contactHistoryEditorController.historyID = viewedHistory.getContacthistoryId();
        System.out.println(viewedHistory.getContacthistoryId());
        return "redirect:/contactHistoryEditor";
    }

    @RequestMapping(value ="/chooseContact", method = RequestMethod.POST)
    public String chooseContact(Contact contact) {
        contactB = contactRepository.findByContactID(contact.getContactID());
        return "/chooseContact";
    }

    @RequestMapping(value ="/createNewRelationship", method = RequestMethod.POST)
    public String createNewRelationship(Contact contact) {
        System.out.println(contact.getContactID());
        RelationshipCreatorController.contactA = contactRepository.findByContactID(contact.getContactID());
        return "redirect:/relationshipCreator";
    }
    @RequestMapping(value = "/deleteOutgoingRelationship", method = RequestMethod.POST)
    public String contactDetails(Relationship relationship) {
        relationshipRepository.deleteById(relationship.getRelationshipID());
        return "redirect:/contacts";
    }
}
