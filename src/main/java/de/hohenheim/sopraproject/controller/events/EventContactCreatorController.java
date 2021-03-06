package de.hohenheim.sopraproject.controller.events;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class EventContactCreatorController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private EventService eventService;

    /**
     * Main method of the Membership Creator
     * Also adds necessary Attributes
     * @param model
     * @return events/EventContactCreator
     */
    @GetMapping("/eventContactCreator/{eventID}")
    public String eventContactCreator(@PathVariable("eventID") Integer contactID, Model model) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventID(contactID);
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("viewTable", false);

        return "events/EventContactCreator";
    }

    /**
     * searches for a Contact to have a membership with
     * uses the findContacts method of the ContactFinder class
     * @param eventDTO
     * @return events/eventContactCreator
     */
    @RequestMapping(value ="/searchEventContact", method = RequestMethod.POST)
    public String searchEventContact(EventDTO eventDTO, Model model) {

        String searchWord = eventDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactService.findAllContacts());
        eventDTO.setFoundEvents(foundContactsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", eventDTO);
        return "events/eventContactCreator";
    }

    /**
     * Sets the Event to have a Membership with
     *
     */
    @RequestMapping(value = "/setEventContact", method = RequestMethod.POST)
    public String setEventContact(EventDTO eventDTO) {

        Event event = eventService.findByEventID(eventDTO.getEventID());

        event.addEventContact(contactService.findByContactID(eventDTO.getContactTempID()));

        eventService.saveEvent(event);
        return "redirect:/eventDetails/"+eventDTO.getEventID();
    }

}
