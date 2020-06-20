package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Contacthistory;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContacthistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class contactHistoryEditorController {
    @Autowired
    public static Integer historyID;
    @Autowired
    private ContacthistoryRepository contacthistoryRepository;

    private Contacthistory contactHistoryTemp;
/*
    @RequestMapping(value = "/contactHistoryEditor", method = RequestMethod.GET)
    public String contactHistory(Model model) {
        contactHistoryTemp = contacthistoryRepository.findById(historyID);
        model.addAttribute("contactHistory", contactHistoryTemp);
        model.addAttribute("contact", new Contact());
        return "contactHistoryEditor";
    }
    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.POST)
    public String contactDetails(Contacthistory contactHistory) {
        contactHistory.setContacthistoryId(historyID);
        contacthistoryRepository.save(contactHistory);
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping(value = "/deleteContactHistory", method = RequestMethod.POST)
    public String deleteDetails(Contacthistory contacthistory) {
        contacthistoryRepository.deleteById(historyID);
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping(value = "/deleteFromContactHistory", method = RequestMethod.POST)
    public String deleteDetails(Contact contact) {
        contactHistoryTemp.contact.getContactID();
        contacthistoryRepository.deleteById(historyID);
        System.out.println("saving");
        return "redirect:/contacts";
    }*/
}
