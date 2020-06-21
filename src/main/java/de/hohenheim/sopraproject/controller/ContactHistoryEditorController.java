package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContactHistoryEditorController {

    public static ContactHistory contactHistory;
    @Autowired
    private ContactHistoryRepository contacthistoryRepository;


    @RequestMapping(value = "/contactHistoryEditor", method = RequestMethod.GET)
    public String contactHistoryEditor(Model model) {
        model.addAttribute("contactHistory", contactHistory);
        return "contacts/contactHistoryEditor";
    }

    @RequestMapping(value = "/savingContactHistory", method = RequestMethod.GET)
    public String savingContactHistory(ContactHistory contactHistory) {
        return "contactHistoryEditor";
    }
}
