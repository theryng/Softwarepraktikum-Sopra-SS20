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
    private boolean existingContactHistories;

    /**
     * Main method for Viewing of Contact Details Site, adds necessary Attributes
     * @param model
     * @return contactDetails
     */
    @RequestMapping(value = "/contactDetails", method = RequestMethod.GET)
    public String contactDetails(Model model) {
        Contact contact = contactRepository.findByContactID(contactID);
        checkTables(contact);
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contact);
        model.addAttribute("viewedHistory", new ContactHistory());
        model.addAttribute("existingRelationships", existingRelationships);
        model.addAttribute("existingContactHistories", existingContactHistories);

        if(foundContacts.size()>0){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);

        return "contacts/contactDetails";
    }

    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(value = "/savingContact", method = RequestMethod.POST)
    public String contactDetails(Contact contact) {
        contact.setContactID(contactID);
        if(!contactRepository.findByContactID(contact.getContactID()).equals(contact)){
            contactRepository.save(contact);
        }

        return "redirect:/contacts";
    }

    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public String deleteDetails(Contact contact) {
        contactRepository.deleteById(contact.getContactID());
        return "redirect:/contacts";
    }

    /**
     *
     * @param viewedHistory
     * @return
     */
    @RequestMapping("/openEditContactHistory")
    public String editContactHistory(ContactHistory viewedHistory) {
        ContactHistoryEditorController.contactHistory = contacthistoryRepository.findByContactHistoryID(viewedHistory.getContactHistoryID());
        return "redirect:/contactHistoryEditor";
    }

    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(value ="/createNewContactHistory", method = RequestMethod.POST)
    public String createNewContactHistory(Contact contact) {
        ContactHistoryCreator1Controller.originalContact = contactRepository.findByContactID(contact.getContactID());
        return "redirect:/contactHistoryCreator1";
    }
    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(value ="/chooseContact", method = RequestMethod.POST)
    public String chooseContact(Contact contact) {
        contactB = contactRepository.findByContactID(contact.getContactID());
        return "/chooseContact";
    }
    /**
     *
     * @param contact
     * @return
     */
    @RequestMapping(value ="/createNewRelationship", method = RequestMethod.POST)
    public String createNewRelationship(Contact contact) {
        RelationshipCreator1Controller.contactA = contactRepository.findByContactID(contact.getContactID());
        return "redirect:/relationshipCreator1";
    }
    /**
     *
     * @param relationship
     * @return
     */
    @RequestMapping(value = "/deleteOutgoingRelationship", method = RequestMethod.POST)
    public String contactDetails(Relationship relationship) {
        Relationship choosenRelationship = relationshipRepository.findByRelationshipID(relationship.getRelationshipID());
        relationshipRepository.deleteById(choosenRelationship.getPartnerRelationship());
        relationshipRepository.deleteById(relationship.getRelationshipID());
        return "redirect:/contactDetails";
    }

    @RequestMapping(value = "/backContactDetails", method = RequestMethod.POST)
    public String backContactDetails() {
        contactID = null;
        return "redirect:/contacts";
    }
    private void checkTables(Contact contact){
        if(contact.outgoingRelationships.size()>0){
            existingRelationships = true;
        }
        else{
            existingRelationships = false;
        }
        if(contact.getContactHistory().size()>0){
            existingContactHistories = true;
        }
        else{
            existingContactHistories = false;
        }
    }
}
