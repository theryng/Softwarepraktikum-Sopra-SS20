package de.hohenheim.sopraproject.controller.contacts;

import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import de.hohenheim.sopraproject.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class RelationshipCreator2Controller {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private ContactService contactService;
    /**
     * Main Method of the second part of the Relationship Creator process
     * Also adds necessary attributes
     * @param model
     * @return relationshipCreator2
     */
    @GetMapping("/relationshipCreator2/{contactID}")
    public String relationshipCreator2Controller(@PathVariable("contactID") Integer contactID, @ModelAttribute("relationshipDTO") RelationshipDTO relationshipDTO , Model model) {
        relationshipDTO.setRelationship(new Relationship());
        relationshipDTO.getRelationship().setContactA(contactService.findByContactID(relationshipDTO.getContactA()));
        relationshipDTO.getRelationship().setContactB(contactService.findByContactID(relationshipDTO.getContactB()));

        model.addAttribute("relationshipDTO", relationshipDTO);

        return "contacts/relationshipCreator2";
    }

    /**
     * Saves the Relationship, as well as creates
     * the Partner Relationship
     * @return contactDetails
     */
    @RequestMapping(value = "/saveRelationship", method = RequestMethod.POST)
    public String saveRelationship( @Valid RelationshipDTO relationshipDTO, BindingResult result){
        relationshipDTO.getRelationship().setContactA(contactService.findByContactID(relationshipDTO.getContactA()));
        relationshipDTO.getRelationship().setContactB(contactService.findByContactID(relationshipDTO.getContactB()));
        Relationship relationship = relationshipDTO.getRelationship();
        if(result.hasErrors()){

            return "contact/relationshipCreator2";
        }
        else{
            Relationship ingoingRelationship = new Relationship();
            if(relationship.getIngoingString() == ""){
                ingoingRelationship.setContactA(relationship.getContactB());
                ingoingRelationship.setContactB(relationship.getContactA());
                ingoingRelationship.setTypeOfRelationship(relationship.getTypeOfRelationship());
                ingoingRelationship.setSince(relationship.getSince());
            }
            else{
                ingoingRelationship.setContactA(relationship.getContactB());
                ingoingRelationship.setContactB(relationship.getContactA());
                ingoingRelationship.setTypeOfRelationship(relationship.getIngoingString());
                ingoingRelationship.setSince(relationship.getSince());
            }
            relationshipRepository.save(relationship);
            relationshipRepository.save(ingoingRelationship);
            relationship.setPartnerRelationship(ingoingRelationship.getRelationshipID());
            ingoingRelationship.setPartnerRelationship(relationship.getRelationshipID());
            relationshipRepository.save(relationship);
            relationshipRepository.save(ingoingRelationship);

            return "redirect:/contactDetails/"+relationship.getContactA().getContactID();
        }

    }

    /**
     * Back Button which returns to the fist part of the Process
     * Returns to the page relationshipCreator1
     * @return relationshipCreator1
     */
    @RequestMapping(value = "/backRelationshipCreator2", method = RequestMethod.POST)
    public String backRelationShipCreator1() {
        return "redirect:/relationshipCreator1";
    }
}
