package de.hohenheim.sopraproject.controller.contacts;

import de.hohenheim.sopraproject.dto.ContactDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.service.ContactHistoryService;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * This controller is used to handle all methods revolving around the html page contactDetails
 *
 * It helps with various method to save new contacts, delete existing ones, open up the contactHistoryEditor, create a new contact history
 * for a certain contact, create a new relationship between two existing contacts or delete a relationship
 *
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class ContactDetailsController {


    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactHistoryService contactHistoryService;

    @Autowired
    private RelationshipService relationshipService;

    /**
     * Main method for Viewing of Contact Details Site, adds necessary Attributes
     * @param model
     * @return contactDetails
     */
    @GetMapping("/contactDetails/{contactID}")
    public String contactDetails(@PathVariable("contactID") Integer contactID, Model model) {
        System.out.println("Testing the stuff " + contactID);
        Contact contact = contactService.findByContactID(contactID);
        System.out.println("Anzahl Tags: " + contact.getTags().size());
        String searchWord = "";
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contact);
        model.addAttribute("viewedHistory", new ContactHistory());
        model.addAttribute("viewContactHistory", checkContactHistoryList(contact.getContactHistory()));
        model.addAttribute("viewRelationship", checkRelationshipList(contact.getOutgoingRelationships()));
        model.addAttribute("searchWord", searchWord);
        return "contacts/contactDetails";
    }

    /**
     * This method creates a new contact and saves it to the existing database
     *
     * A new contact will be created with this method. It also checks if the given ID is already mapped to a different
     * existing contact. As long thats not the case a new contact will be saved to the database. Once the contact is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param contact
     * @return redirect:/contacts
     */
    @PostMapping("/savingContact")
    public String contactDetails(@ModelAttribute("contact") @Valid Contact contact, BindingResult result) {
        System.out.println(contact.getContactID());
        if(result.hasErrors()){
            return "contactDetails";
        }
        else{
            contact.setContactID(contact.getContactID());
            if(!contactService.findByContactID(contact.getContactID()).equals(contact.getContactID())){
                contactService.saveContact(contact);
            }
            return "redirect:/contactDetails/"+contact.getContactID();
        }
    }

    /**
     * This method deletes an existing contact inside the database
     *
     * An existing contact will be deleted. The corresponding contactID will also be deleted so new contacts can get this
     * ID in the future. Once the contact is deleted the page will be reloaded to update the contact table.
     *
     * @param contact
     * @return redirect:/contacts
     */

    @RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
    public String deleteDetails(Contact contact) {
        contactService.deleteByContactID(contact.getContactID());
        return "redirect:/contacts";
    }

    /**
     * This method creates a new contactHistory
     *
     *
     * @param contact
     * @return redirect:/contactHistoryCreator1
     */
    @RequestMapping(value ="/createNewContactHistory", method = RequestMethod.POST)
    public String createNewContactHistory(Contact contact,@ModelAttribute("mapping1Form") final Model model, final RedirectAttributes redirectAttributes) {

        return "redirect:/contactHistoryCreator1";
    }


    /**
     * This method deletes an outgoing relationship
     *
     * This method deletes an existing outgoing relationship from a contact to another. Once finished the page will be reloaded
     * in order to update the relationships of the specific contact.
     *
     * @param relationship
     * @return redirect:/contactDetails
     */
    @RequestMapping(value = "/deleteOutgoingRelationship", method = RequestMethod.POST)
    public String contactDetails(Relationship relationship) {

        Relationship choosenRelationship = relationshipService.findByRelationshipID(relationship.getRelationshipID());
        Integer id = choosenRelationship.getContactA().getContactID();
        if(!(choosenRelationship.getPartnerRelationship()==0)){
            relationshipService.deleteByRelationshipID(choosenRelationship.getPartnerRelationship());
        }

        relationshipService.deleteByRelationshipID(relationship.getRelationshipID());
        return "redirect:/contactDetails/"+id;
    }

    @GetMapping("/deleteContactTag/{tagID}/{contactID}")
    public String deleteContactTag(@PathVariable("tagID") Integer tagID, @PathVariable("contactID") Integer contactID) {
        List<Tags> tags = contactService.findByContactID(contactID).getTags();
        System.out.println("Number of Tags" + tags.size());
        for(Tags tag : tags){
            if(tag.getTagsID() == tagID){
                tags.remove(tag);
            }
        }

        return "contactDetails/"+contactID;
    }
    /**
     * This method exits the contactDetails
     *
     * This method exists the contactDetails page by clicking on a corresponding button bounded with this method. Once clicked
     * the page will be redirected to the contact page.
     *
     * @return redirect:/contacts
     */
    @RequestMapping(value = "/backContactDetails", method = RequestMethod.POST)
    public String backContactDetails() {
        return "redirect:/contacts";
    }

    private boolean checkContactHistoryList(Set<ContactHistory> list){
        if(list.size()>0){
            return true;
        }
        return false;
    }

    private boolean checkRelationshipList(Set<Relationship> list){
        if(list.size()>0){
            return true;
        }
        return false;
    }
}