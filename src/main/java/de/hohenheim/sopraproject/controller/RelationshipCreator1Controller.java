package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class RelationshipCreator1Controller {

    @Autowired
    private ContactService contactService;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return relationshipCreator1
     */
    @GetMapping("/relationshipCreator1/{contactID}")
    public String relationshipCreator1Controller(@PathVariable("contactID") Integer contactID, Model model) {
        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setContactA(contactID);
        model.addAttribute("relationshipDTO", relationshipDTO);

        return "contacts/relationshipCreator1";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param relationshipDTO
     * @return relationshipCreator1
     */
    @RequestMapping(value ="/searchRelationshipContact", method = RequestMethod.POST)
    public String searchContacts(RelationshipDTO relationshipDTO, Model model) {
        System.out.println(relationshipDTO.getSearchWord());
        System.out.println(relationshipDTO.getContactA());
        String searchWord = relationshipDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactService.findAllContacts());
        relationshipDTO.setFoundContact(foundContactsTemp);

        model.addAttribute("relationshipDTO", relationshipDTO);
        return "contacts/relationshipCreator1";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setContactB", method = RequestMethod.POST)
    public String setContactB(RelationshipDTO relationshipDTO, RedirectAttributes redirectAttributes) {
        System.out.println(relationshipDTO.getContactB());
        System.out.println(relationshipDTO.getContactA());
        System.out.println("redirecting to PArt 2");
        redirectAttributes.addFlashAttribute("relationshipDTO", relationshipDTO);
        return "redirect:/relationshipCreator2/"+relationshipDTO.getContactB();
    }

    /**
     * Back Button which returns the User to the contactDetails page
     * Also Resets the page
     * @return contactDetails
     */
    @RequestMapping(value = "/backRelationshipCreator1", method = RequestMethod.POST)
    public String backRelationShipCreator1() {
        return "redirect:/contactDetails";
    }

}