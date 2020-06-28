package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class RelationshipCreator1Controller {

    public static Contact contactA;
    private Relationship relationshipTemp;
    @Autowired
    private ContactRepository contactRepository;
    private String searchWord;
    private Set<Contact> foundContacts = new HashSet<>();
    private boolean viewTable;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return relationshipCreator1
     */
    @RequestMapping(value = "/relationshipCreator1", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
        relationshipTemp = new Relationship();
        relationshipTemp.setContactA(contactA);
        model.addAttribute("relationship", relationshipTemp);
        model.addAttribute("viewTable", viewTable);

        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);

        return "contacts/relationshipCreator1";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param searchWord
     * @return relationshipCreator1
     */
    @RequestMapping(value ="/searchRelationshipContact", method = RequestMethod.POST)
    public String searchContacts(String searchWord) {
        System.out.println(searchWord);
        ContactFinder findContact = new ContactFinder();
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        foundContactsTemp.remove(contactRepository.findByContactID(contactA.getContactID()));
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTable = true;
        }
        else{
            foundContacts.clear();
            viewTable = false;
        }
        return "redirect:/relationshipCreator1";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @param contact
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setContactB", method = RequestMethod.POST)
    public String setContactB(Contact contact) {
        relationshipTemp.setContactB(contactRepository.findByContactID(contact.getContactID()));
        RelationshipCreator2Controller.relationshipTemp = relationshipTemp;
        System.out.println("redirecting to PArt 2");
        return "redirect:/relationshipCreator2";
    }

    /**
     * Back Button which returns the User to the contactDetails page
     * Also Resets the page
     * @return contactDetails
     */
    @RequestMapping(value = "/backRelationshipCreator1", method = RequestMethod.POST)
    public String backRelationShipCreator1() {
        foundContacts.clear();
        viewTable = false;
        return "redirect:/contactDetails";
    }

}