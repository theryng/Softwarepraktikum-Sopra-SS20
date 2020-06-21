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

@Controller
public class RelationshipCreatorController {

    public static Contact contactA;
    private Relationship relationshipTemp;

    @Autowired
    private ContactRepository contactRepository;

    private String searchWord;
    private Set<Contact> foundContacts;
    private String choosenContact;

    @RequestMapping(value = "/relationshipCreator", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
        relationshipTemp = new Relationship();
        relationshipTemp.setContactA(contactA);
        model.addAttribute("relationship", relationshipTemp);

        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("choosenContact", choosenContact);

        return "relationshipCreator";
    }

    @RequestMapping(value ="/searchContact", method = RequestMethod.POST)
    public String searchContacts(String searchWord) {
        System.out.println(searchWord);

        ContactFinder findContact = new ContactFinder();
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
        }
        else{
            foundContacts.clear();
            System.out.println("No Contacts Found");
        }
        return "redirect:/relationshipCreator";
    }

    @RequestMapping(value = "/setContactB", method = RequestMethod.POST)
    public String setContactB(Contact contact) {
        relationshipTemp.setContactB(contactRepository.findByContactID(contact.getContactID()));
        choosenContact = relationshipTemp.getContactB().getFirstname() + " " + relationshipTemp.getContactB().getLastname();

        return "redirect:/relationshipCreator";
    }

    @RequestMapping(value = "/saveRelationship", method = RequestMethod.POST)
    public String saveRelationship(Relationship relationship) {
        relationshipTemp.setSince(relationship.getSince());
        relationshipTemp.setTypeOfRelationship(relationship.getTypeOfRelationship());

        return "redirect:/contactDetails";
    }
}
