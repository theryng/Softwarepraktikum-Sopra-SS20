package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ContactDetailsController {

    public static Integer contactID;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactHistoryRepository contacthistoryRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    private String searchWord;

    private Set<Contact> foundContacts = new HashSet<>();

    private Contact contactB;

    private boolean existingRelationships;
    @RequestMapping(value = "/contactDetails", method = RequestMethod.GET)
    public String contactDetails(Model model) {
        Contact contact = contactRepository.findByContactID(contactID);

        if(contact.outgoingRelationships.size()>0){
            existingRelationships = true;
        }
        else{
            existingRelationships = false;
        }
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contact);
        model.addAttribute("viewedHistory", new ContactHistory());
        model.addAttribute("existingRelationships", existingRelationships);
        System.out.println("Test");

        if(foundContacts.size()>0){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);

        return "contacts/contactDetails";
    }
    @RequestMapping(value = "/savingContact", method = RequestMethod.POST)
    public String contactDetails(Contact contact) {
        contact.setContactID(contactID);
        contactRepository.save(contact);
        System.out.println("saving");
        return "redirect:/contacts/contacts";
    }
    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public String deleteDetails(Contact contact) {
        contactRepository.deleteById(contact.getContactID());
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping("/openEditContactHistory")
    public String editContactHistory(ContactHistory viewedHistory) {
        System.out.println("View History");
        ContactHistoryEditorController.contactHistory = contacthistoryRepository.findByContactHistoryID(viewedHistory.getContactHistoryID());
        System.out.println(viewedHistory.getContactHistoryID());
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
        RelationshipCreator1Controller.contactA = contactRepository.findByContactID(contact.getContactID());
        return "redirect:/relationshipCreator1";
    }
    @RequestMapping(value = "/deleteOutgoingRelationship", method = RequestMethod.POST)
    public String contactDetails(Relationship relationship) {
        relationshipRepository.deleteById(relationship.getRelationshipID());
        return "redirect:/contacts/contacts";
    }
}
