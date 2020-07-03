package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This controller contains the methods to create a new contact
 *
 * This controller contains all important methods to create a new contact and saving it inside the database.
 * It allows to get all informations about a contact, to save a newly created contact and to look up all the details
 * about a certain contact.
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class TagsController {
    @Autowired
    ContactService contactService;
    @Autowired
    EditingHistoryService editingHistoryService;
    @Autowired
    private TagsService tagsService;

    /**
     * This method gets all the information about a contact
     *
     * This method gives out all the information about the contacts stored inside the database
     *
     * @param model
     * @return contacts
     */
    @GetMapping("/tags/{objectType}/{id}")
    public String tags(@PathVariable("objectType") String objectType, @PathVariable("id") int id, Model model) {
        String searchword = "";
        List<Tags> allTags = tagsService.findAllTags();
        boolean showList = false;
        if(allTags.size()>0){
            showList = true;
        }
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(id);
        tagsDTO.setType(objectType);
        model.addAttribute("tagsDTO", tagsDTO);
        model.addAttribute("showList", showList);
        model.addAttribute("allTags", allTags);

        return "tags/tags";
    }

    /**
     * This method saves a new contact
     *
     * This method saves a newly created contact to the database. It also merges the temporary attributes of ZipCode, City,
     * Street and houseNumber to one single attribute called address. Once the new contact was saved to the database the page will
     * be reloaded and the table will be updated. The new contact will no show up on the page contacts.
     *
     * @param tagsDTO
     * @return redirect:/contacts
     */
    @RequestMapping(value="/saveTag", method = RequestMethod.POST)
    public String saveContact(@Valid TagsDTO tagsDTO, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("Fehler");

            model.addAttribute("allTags", tagsService.findAllTags());

        }
        else{
            tagsService.saveTags(tagsDTO.getTag());
            model.addAttribute("allTags", tagsService.findAllTags());

        }
        model.addAttribute("tagsDTO", tagsDTO);
        model.addAttribute("showList", true);

        return "tags/tags";

    }

    /**
     *  Method which can be used to search for a certain Contact.
     *  Calls the Contact Finder, and uses a searchWord to find a Contact.
     *  Reloads the Site at the very End.
     * @param
     * @return contactHistoryCreator1
     */
    @PostMapping(value ="/searchTag")
    public String searchContacts(TagsDTO tagsDTO, Model model) {
        ContactFinder findContact = new ContactFinder();

        LinkedList<Tags> foundTagsTemp = findContact.findTags(tagsDTO.getSearchWord(), tagsService.findAllTags());

        boolean showList = false;
        if(foundTagsTemp.size()>0){
            showList = true;
        }
        model.addAttribute("showList", showList);
        model.addAttribute("allTags", foundTagsTemp);
        model.addAttribute("tagsDTO", tagsDTO);

        return "tags/tags";
    }

    @PostMapping(value ="/setTag")
    public String setTag(TagsDTO tagsDTO, Model model) {
        System.out.println("setting tag");
        System.out.println(tagsDTO.getType());
        String objectType = tagsDTO.getType();
        Integer id = Integer.valueOf(tagsDTO.getOriginalID());
        if(objectType.equals("contact")){
            System.out.println("Type Contact");
            Contact contact = contactService.findByContactID(id);
            Tags tag = tagsService.findByTagID(Integer.valueOf(tagsDTO.getTagID()));
            tag.getContacts().add(contact);
            tagsService.saveTags(tag);
            contactService.saveContact(contact);
            return "redirect:/contactDetails/"+tagsDTO.getOriginalID();
        }
        System.out.println("Fehler im Setten");
        //TBD IF For other Types like Insitute/Events etc.
        return null;
    }
}