package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
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
public class ContactHistoryEditorController {

    public static ContactHistory contactHistory;
    @Autowired
    private ContactHistoryRepository contacthistoryRepository;
    @Autowired
    private ContactRepository contactRepository;

    private String searchWord;
    private Set<Contact> foundContacts;
    private boolean viewTable;


    @RequestMapping(value = "/contactHistoryEditor", method = RequestMethod.GET)
    public String contactHistoryEditor(Model model) {
        if(foundContacts != null){
            model.addAttribute("allContacts", foundContacts);
        }
        else{
            model.addAttribute("allContacts", new HashSet<Contact>());
        }
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("viewTable", viewTable);
        model.addAttribute("contactHistory", contactHistory);

        return "contacts/contactHistoryEditor";
    }

    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.POST)
    public String savingContactHistory(ContactHistory contactHistoryTemp) {
        contactHistory.setDate(contactHistoryTemp.getDate());
        contactHistory.setText(contactHistoryTemp.getText());
        foundContacts.clear();
        return "redirect:/contactDetails";
    }

    @RequestMapping(value = "/deleteContactHistory", method = RequestMethod.POST)
    public String deleteContactHistory(ContactHistory contactHistory) {

        contacthistoryRepository.deleteById(contactHistory.getContactHistoryID());
        foundContacts.clear();
        return "redirect:/contactDetails";
    }
    @RequestMapping(value = "/deleteContactFromHistory", method = RequestMethod.POST)
    public String deleteFromContactHistory(Contact contact) {
        Set<Contact> contacts = contactHistory.getContactOfHistory();
        Contact tempContact = new Contact();
        int contactIDA = contact.getContactID();
        for(Contact con : contacts){
            int contactIDB = con.getContactID();
            if(contactIDA == contactIDB){
                tempContact = con;
            }
        }
        try{
            contacts.remove(tempContact);
        } catch (Exception e) {
            System.out.println("Löschen nicht möglich");
        }

        return "redirect:/contactHistoryEditor";
    }
    @RequestMapping(value = "/addContactToHistory", method = RequestMethod.POST)
    public String addToContactHistory(Contact contact) {
        Contact addedContact = contactRepository.findByContactID(contact.getContactID());
        if(addedContact != null){
            contactHistory.getContactOfHistory().add(addedContact);
        }

        return "redirect:/contactHistoryEditor";
    }

    @RequestMapping(value ="/searchContactForHistoryEditor", method = RequestMethod.POST)
    public String searchContactsForHistoryEditor(String searchWord) {
        System.out.println(searchWord);

        ContactFinder findContact = new ContactFinder();
        Set<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactRepository.findAll());
        if(foundContactsTemp.size()>0){
            foundContacts = foundContactsTemp;
            viewTable = true;
        }
        else{
            foundContacts.clear();
            viewTable = false;
        }
        return "redirect:/contactHistoryEditor";
    }

}
