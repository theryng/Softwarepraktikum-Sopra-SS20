package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Contacthistory;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContacthistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactDetailsController {

    @Autowired
    public static Integer contactID;
    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping(value = "/contactDetails", method = RequestMethod.GET)
    public String contactDetails(Model model) {
        model.addAttribute("contact", contactRepository.findByContactID(contactID));
        model.addAttribute("history", new Contacthistory());
        System.out.println("Test");
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
    @RequestMapping("/editContactHistory")
    public String editContactHistory(Contacthistory contacthistory) {
        System.out.println(contacthistory.getContacthistoryId());
        contactHistoryEditorController.historyID = contacthistory.getContacthistoryId();
        ContactDetailsController.contactID = contactID;
        System.out.println(ContactDetailsController.contactID);
        return "redirect:/contactDetails";
    }
}
