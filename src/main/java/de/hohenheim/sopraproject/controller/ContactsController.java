package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Address;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Contacthistory;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    private ContactService contactService;
    @RequestMapping(value ="/contacts", method = RequestMethod.GET)
    public String contacts(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("allContacts", contactRepository.findAll());
        System.out.println("On to Contacts");
        return "contacts";
    }
    @RequestMapping(value="/saveContact", method = RequestMethod.POST)
    public String saveContact(Contact contact){
        System.out.println("saving contact");
        contact.setAddress(new Address(contact.getTempZipCode(), contact.getTempCity(), contact.getTempStreet() , contact.getTempHouseNmbr()));
        contactRepository.save(contact);
        return "home";
    }
    @RequestMapping("/allContacts")
    public String allContacts(Model model) {
        model.addAttribute("allContacts", contactRepository.findAll());
        return "contacts";
    }

}