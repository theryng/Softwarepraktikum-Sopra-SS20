package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class RelationshipCreator2Controller {

    public static Relationship relationshipTemp;
    private String choosenContact;
    @Autowired
    private RelationshipRepository relationshipRepository;
    private String ingoingString;

    /**
     * Main Method of the second part of the Relationship Creator process
     * Also adds necessary attributes
     * @param model
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/relationshipCreator2", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
        choosenContact = relationshipTemp.getContactB().getFirstname() + " " + relationshipTemp.getContactB().getLastname();
        model.addAttribute("relationship", relationshipTemp);
        model.addAttribute("choosenContact", choosenContact);
        return "contacts/relationshipCreator2";
    }

    /**
     * Saves the Relationship, as well as creates
     * the Partner Relationship
     * @param relationship
     * @return contactDetails
     */
    @RequestMapping(value = "/saveRelationship", method = RequestMethod.POST)
    public String saveRelationship(Relationship relationship){

        relationship.setContactA(relationshipTemp.getContactA());
        relationship.setContactB(relationshipTemp.getContactB());
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

        return "redirect:/contactDetails";
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
