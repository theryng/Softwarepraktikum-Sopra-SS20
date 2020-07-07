package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import de.hohenheim.sopraproject.service.InstituteService;
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
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return institutes/instituteContactCreator
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
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param eventDTO
     * @return institutes/instituteContactCreator
     */
    @RequestMapping(value ="/searchEventContact", method = RequestMethod.POST)
    public String searchEventContact(EventDTO eventDTO, Model model) {
        System.out.println(eventDTO.getSearchWord());
        System.out.println(eventDTO.getEventID());
        String searchWord = eventDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Contact> foundContactsTemp = findContact.findContacts(searchWord, contactService.findAllContacts(), "Name");
        eventDTO.setFoundEvents(foundContactsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", eventDTO);
        return "events/eventContactCreator";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/setEventContact", method = RequestMethod.POST)
    public String setEventContact(EventDTO eventDTO) {
        System.out.println("IDS für die Events:");
        System.out.println(eventDTO.getContactTempID());
        System.out.println(eventDTO.getEventID());
        Event event = eventService.findByEventID(eventDTO.getEventID());
        System.out.println("Größen Liste");
        System.out.println(event.getContacts().size());
        event.addEventContact(contactService.findByContactID(eventDTO.getContactTempID()));
        System.out.println(event.getContacts().size());
        eventService.saveEvent(event);
        return "redirect:/eventDetails/"+eventDTO.getEventID();
    }

}
