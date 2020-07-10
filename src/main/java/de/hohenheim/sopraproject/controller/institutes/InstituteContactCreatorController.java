package de.hohenheim.sopraproject.controller.institutes;

import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.InstituteService;
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
public class InstituteContactCreatorController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private InstituteService instituteService;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return institutes/instituteContactCreator
     */
    @GetMapping("/instituteContactCreator/{contactID}")
    public String instituteContactCreator(@PathVariable("contactID") Integer contactID, Model model) {
        InstituteDTO instituteDTO = new InstituteDTO();
        instituteDTO.setInstituteID(contactID);
        model.addAttribute("instituteDTO", instituteDTO);
        model.addAttribute("viewTable", false);

        return "institutes/instituteContactCreator";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param instituteDTO
     * @return institutes/instituteContactCreator
     */
    @RequestMapping(value ="/searchInstituteContact", method = RequestMethod.POST)
    public String searchInstituteContact(InstituteDTO instituteDTO, Model model) {
        System.out.println(instituteDTO.getSearchWord());
        System.out.println(instituteDTO.getInstituteID());
        String searchWord = instituteDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactService.findAllContacts());
        instituteDTO.setFoundInstitutes(foundContactsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", instituteDTO);
        return "institutes/instituteContactCreator";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setInstituteContact", method = RequestMethod.POST)
    public String setInstituteContact(InstituteDTO instituteDTO) {
        System.out.println("IDS für die Institute:");
        System.out.println(instituteDTO.getContactTempID());
        System.out.println(instituteDTO.getInstituteID());
        Institute institut = instituteService.findByInstitutesID(instituteDTO.getInstituteID());
        institut.addInstitutionContacts(contactService.findByContactID(instituteDTO.getContactTempID()));
        instituteService.saveInstitute(institut);
        return "redirect:/instituteDetails/"+instituteDTO.getInstituteID();
    }
}