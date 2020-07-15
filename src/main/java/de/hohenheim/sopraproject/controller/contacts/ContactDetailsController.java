package de.hohenheim.sopraproject.controller.contacts;

import de.hohenheim.sopraproject.dto.ContactDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private EditingHistoryService editingHistoryService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TagsService tagsService;

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

        Contact contact = contactService.findByContactID(contactID);
        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(contactID);
        if(contact.getTags().size()>0){
            model.addAttribute("viewTags", true);
        }
        else{
            model.addAttribute("viewTags", false);
        }
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contact);
        model.addAttribute("viewedHistory", new ContactHistory());
        model.addAttribute("viewContactHistory", checkContactHistoryList(contact.getContactHistory()));
        model.addAttribute("viewRelationship", checkRelationshipList(contact.getOutgoingRelationships()));
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("tagDTO", tagsDTO);
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
    public String contactDetails(@ModelAttribute("contact") @Valid Contact contact, BindingResult result, RedirectAttributes redirectAttributes, Principal principal) {
        if(result.hasErrors()){
            return "redirect:/contactDetails/"+contact.getContactID();
        }
        else{
            contact.setContactID(contact.getContactID());
            if(!contactService.findByContactID(contact.getContactID()).equals(contact.getContactID())){
                contactService.saveContact(contact);

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();

                editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Kontaktdetails von: " + contact.getFirstname() + " " + contact.getLastname(), dateFormat.format(date)));

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
     * This method links to the creation of a new ContactHistory
     *
     * @param contact
     * @return redirect:/contactHistoryCreator1
     */
    @RequestMapping(value ="/createNewContactHistory", method = RequestMethod.POST)
    public String createNewContactHistory(Contact contact,@ModelAttribute("mapping1Form") final Model model, final RedirectAttributes redirectAttributes, Principal principal) {
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

    /**
     * This function deletes the selected Tag from the Contact
     * @param tagsDTO
     * @return
     */
    @GetMapping("/deleteContactTag")
    public String deleteContactTag(TagsDTO tagsDTO) {
        List<Tags> tags = contactService.findByContactID(tagsDTO.getOriginalID()).getTags();
        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){

                    removeTag = tag;
            }
        }
        tags.remove(removeTag);

        Contact contact = contactService.findByContactID(tagsDTO.getOriginalID());
        contact.setTags(tags);
        contactService.saveContact(contact);
        Tags tag = tagsService.findByTagID(removeTag.getTagsID());
        tag.getContacts().remove(contact);
        tagsService.saveTags(tag);
        return "redirect:/contactDetails/"+contact.getContactID();
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

    /**
     * Help method which checks for ContactHistories in the Contact
     * @param list
     * @return
     */
    private boolean checkContactHistoryList(Set<ContactHistory> list){
        if(list.size()>0){
            return true;
        }
        return false;
    }

    /**
     * Help Method which checks for Relationships in the Contact
     * @param list
     * @return
     */
    private boolean checkRelationshipList(Set<Relationship> list){
        if(list.size()>0){
            return true;
        }
        return false;
    }
}