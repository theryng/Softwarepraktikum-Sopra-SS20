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

@Controller
public class contactHistoryEditorController {
    @Autowired
    public static Integer historyID;
    @Autowired
    private ContacthistoryRepository contacthistoryRepository;

    @RequestMapping(value = "/editContactHistory", method = RequestMethod.GET)
    public String contactHistory(Model model) {
        model.addAttribute("contacthistory", contacthistoryRepository.findById(historyID));
        return "editContactHistory";
    }
    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.POST)
    public String contactDetails(Contacthistory contacthistory) {
        contacthistory.setContacthistoryId(historyID);
        contacthistoryRepository.save(contacthistory);
        System.out.println("saving");
        return "redirect:/contacts";
    }
    @RequestMapping(value = "/deleteContactHistory", method = RequestMethod.POST)
    public String deleteDetails(Contacthistory contacthistory) {
        contacthistoryRepository.deleteById(historyID);
        System.out.println("saving");
        return "redirect:/contacts";
    }
}
