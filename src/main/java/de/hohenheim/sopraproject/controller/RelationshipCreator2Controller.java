package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RelationshipCreator2Controller {

    public static Relationship relationshipTemp;
    private String choosenContact;

    @Autowired
    private RelationshipRepository relationshipRepository;


    @RequestMapping(value = "/relationshipCreator2", method = RequestMethod.GET)
    public String relationshipCreatorController(Model model) {
        choosenContact = relationshipTemp.getContactB().getFirstname() + " " + relationshipTemp.getContactB().getLastname();
        model.addAttribute("relationship", relationshipTemp);
        model.addAttribute("choosenContact", choosenContact);
        return "contacts/relationshipCreator2";
    }
    @RequestMapping(value = "/saveRelationship", method = RequestMethod.POST)
    public String saveRelationship(Relationship relationship){

        relationship.setContactA(relationshipTemp.getContactA());
        relationship.setContactB(relationshipTemp.getContactB());
        relationshipRepository.save(relationship);

        return "redirect:/contactDetails";
    }
}
