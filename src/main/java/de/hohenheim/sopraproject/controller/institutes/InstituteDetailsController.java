package de.hohenheim.sopraproject.controller.institutes;

import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.repository.ContactHistoryRepository;
import de.hohenheim.sopraproject.repository.InstituteRepository;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.InstituteService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page instituteDetails
 *
 * It helps with various method to save new Institutes, delete existing ones, open up the InstituteContactCreator.
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class InstituteDetailsController {



    @Autowired
    private InstituteService instituteService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Main method for Viewing of Institute Details Site, adds necessary Attributes
     * @param model
     * @return instituteDetails
     */
    @RequestMapping(value = "/instituteDetails/{instituteID}", method = RequestMethod.GET)
    public String instituteDetails(@PathVariable("instituteID") Integer instituteID, Model model) {
        System.out.println("Testing the stuff " + instituteID);
        InstituteDTO instituteDTO = new InstituteDTO();
        Institute institute = instituteService.findByInstitutesID(instituteID);
        System.out.println("Anzahl Tags: " + institute.getTags().size());
        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(instituteID);
        instituteDTO.setInstitute(institute);
        instituteDTO.setInstituteID(instituteDTO.getInstitute().getInstituteID());
        model.addAttribute("allContacts", institute.getContacts());
        model.addAttribute("instituteDTO", instituteDTO);
        model.addAttribute("institute", institute);
        model.addAttribute("viewTable", true);
        model.addAttribute("tagDTO", tagsDTO);
        return "institutes/instituteDetails";
    }

    /**
     * This method saves the Institutes and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing institute. As long thats not the case a new institute will be saved to the database. Once the institute is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param instituteDTO
     * @return redirect:/institutes
     */
    @RequestMapping(value = "/savingInstitute", method = RequestMethod.POST)
    public String savingInstitute(@Valid InstituteDTO instituteDTO, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "institutes/instituteDetails";
        }
        else{
            Institute institute = instituteDTO.getInstitute();
            institute.setInstituteID(instituteDTO.getInstituteID());
            if(!instituteService.findByInstitutesID(institute.getInstituteID()).equals(institute)){
                instituteService.saveInstitute(institute);
            }
            instituteDTO.setInstitute(institute);
            model.addAttribute("instituteDTO", instituteDTO);
            return "institutes/instituteDetails";
        }
    }

    @RequestMapping(value = "/deleteInstitute", method = RequestMethod.POST)
    public String deleteInstitute(InstituteDTO instituteDTO) {
        instituteService.deleteByInstituteID(instituteDTO.getInstituteID());
        return "redirect:/institutes";
    }

    @RequestMapping(value = "/deleteContactFromInstitute", method = RequestMethod.POST)
    public String deleteContactFromInstitute(InstituteDTO instituteDTO, Model model) {
        Institute institute = instituteService.findByInstitutesID(instituteDTO.getInstituteID());
        Set<Contact> contacts = institute.getContacts();
        System.out.println(contacts.size());
        Contact deleteContact = contactService.findByContactID(instituteDTO.getContactTempID());
        contacts.remove(deleteContact);
        System.out.println(contacts.size());
        institute.setContacts(contacts);
        instituteService.saveInstitute(institute);
        instituteDTO.setInstitute(institute);
        model.addAttribute("instituteDTO", instituteDTO);
        model.addAttribute("viewTable", checkTables(institute));
        return "institutes/instituteDetails";
    }


    @GetMapping("/deleteInstituteTag")
    public String deleteInstituteTag(TagsDTO tagsDTO) {
        List<Tags> tags = instituteService.findByInstitutesID(tagsDTO.getOriginalID()).getTags();
        System.out.println("Number of Tags 1" + tags.size());
        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){
                System.out.println("remove");
                removeTag = tag;
            }
        }
        tags.remove(removeTag);

        System.out.println("Number of Tags 2" + tags.size());
        Institute institute = instituteService.findByInstitutesID(tagsDTO.getOriginalID());
        institute.setTags(tags);
        System.out.println(institute.getTags().size());
        instituteService.saveInstitute(institute);
        Tags tag = tagsService.findByTagID(removeTag.getTagsID());
        tag.getInstitutes().remove(institute);
        tagsService.saveTags(tag);
        return "redirect:/instituteDetails/"+institute.getInstituteID();
    }



    private boolean checkTables(Institute institute){
        if(institute.getContacts().size()>0){
            return true;
        }
        return false;
    }
}