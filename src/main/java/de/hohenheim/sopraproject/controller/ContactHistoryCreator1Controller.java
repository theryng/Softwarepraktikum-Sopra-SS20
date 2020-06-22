package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
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
public class ContactHistoryCreator1Controller {

    public static Contact originalContact;
    private ContactHistory contactHistoryTemp;

    @Autowired
    private ContactRepository contactRepository;

    private String searchWord;
    private Set<Contact> foundContacts;
    private Set<Contact> choosenContacts = new HashSet<>();

    private boolean viewTableHistories;
    private boolean viewChoosenTable;

    @RequestMapping(value = "/contactHistoryCreator1", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
        contactHistoryTemp = new ContactHistory();
        contactHistoryTemp.setContactOfHistory(foundContacts);
        model.addAttribute("foundContacts", contactHistoryTemp);
        model.addAttribute("choosenContacts", choosenContacts);
        model.addAttribute("viewTableHistories", viewTableHistories);
        model.addAttribute("viewChoosenTable", viewChoosenTable);

        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);

        return "contacts/contactHistoryCreator1";
    }

    @RequestMapping(value ="/searchContactForHistory", method = RequestMethod.POST)
    public String searchContacts(String searchWord) {
        System.out.println(searchWord);

        ContactFinder findContact = new ContactFinder();
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTableHistories = true;

        }
        else{
            foundContacts.clear();
            viewTableHistories = false;
        }
        return "redirect:/contactHistoryCreator1";
    }

    @RequestMapping(value = "/chooseContactForHistory", method = RequestMethod.POST)
    public String setContactB(Contact contact) {
        Contact selectedContact = contactRepository.findByContactID(contact.getContactID());
        boolean exists = false;
        for(Contact con : choosenContacts){
            if(con.getContactID().equals(selectedContact.getContactID())){
                exists = true;
            }
        }
        if(!exists){
            choosenContacts.add(selectedContact);
            viewChoosenTable = true;
        }

        return "redirect:/contactHistoryCreator1";
    }
    @RequestMapping(value = "/submitChoosenContacts", method = RequestMethod.POST)
    public String submitChoosenContacts() {

        ContactHistoryCreator2Controller.choosenContacts=choosenContacts;

        return "redirect:/contactHistoryCreator2";
    }
}
