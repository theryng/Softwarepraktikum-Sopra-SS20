package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import de.hohenheim.sopraproject.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class EventDetailsController {

    public static Integer eventID;

    @GetMapping("/eventDetails")
    public String showHome(Model model) {
        return "eventDetails";
    }

    @Autowired
    private EventService eventService;

    @Autowired
    private ContactService contactService;

    /**

     *
    @GetMapping("/contactDetails/{contactID}")
    public String contactDetails(@PathVariable("contactID") Integer contactID, Model model) {
        System.out.println("Testing the stuff " + contactID);
        Contact contact = contactService.findByContactID(contactID);

        String searchWord = "";
        model.addAttribute("relationship", new Relationship());
        model.addAttribute("contact", contact);
        model.addAttribute("viewedHistory", new ContactHistory());
        model.addAttribute("viewContactHistory", checkContactHistoryList(contact.getContactHistory()));
        model.addAttribute("viewRelationship", checkRelationshipList(contact.getOutgoingRelationships()));
        model.addAttribute("searchWord", searchWord);
        return "contacts/contactDetails";
    }

    *
     * This method creates a new contact and saves it to the existing database
     *
     * A new contact will be created with this method. It also checks if the given ID is already mapped to a different
     * existing contact. As long thats not the case a new contact will be saved to the database. Once the contact is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param
     * @return redirect:/contacts
     */
    @PostMapping("/savingEvent")
    public String eventDetails(@ModelAttribute("event") @Valid Event event, BindingResult result) {
        System.out.println(event.getEventId());
        if(result.hasErrors()){
            return "eventDetails";
        }
        else{
            event.setEventId(event.getEventId());
            if(!eventService.findByEventId(event.getEventId()).equals(event.getEventId())){
                eventService.saveEvent(event);
            }
            return "redirect:/eventDetails/"+event.getEventId();
        }
    }



    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteDetails(Event event) {
        eventService.deleteByEventId(event.getEventId());
        return "redirect:/events";
    }






}
