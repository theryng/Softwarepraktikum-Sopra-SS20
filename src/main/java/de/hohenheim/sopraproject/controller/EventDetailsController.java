package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class EventDetailsController {



    @Autowired
    private EventService eventService;

    @Autowired
    private ContactService contactService;

    @GetMapping(value = "/eventDetails/{eventID}")
    public String eventDetails(@PathVariable("eventID") Integer eventID, Model model) {
        System.out.println("Testing the stuff " + eventID);
        EventDTO eventDTO = new EventDTO();
        Event event = eventService.findByEventID(eventID);
        eventDTO.setEvent(event);
        eventDTO.setEventID(eventDTO.getEvent().getEventID());
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("viewTable", checkTables(event));
        return "events/eventDetails";
    }

    /**
     * This method saves the Institutes and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing institute. As long thats not the case a new institute will be saved to the database. Once the institute is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param eventDTO
     * @return redirect:/institutes
     */
    @RequestMapping(value = "/savingEvent", method = RequestMethod.POST)
    public String savingEvent(@Valid EventDTO eventDTO, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "events/eventDetails";
        }
        else{
            Event event =eventDTO.getEvent();
            event.setEventID(eventDTO.getEventID());
            if(!eventService.findByEventID(event.getEventID()).equals(event)){
                eventService.saveEvent(event);
            }
           eventDTO.setEvent(event);
            model.addAttribute("eventDTO", eventDTO);
            return "events/eventDetails";
        }
    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteEvent(EventDTO eventDTO) {
        eventService.deleteByEventID(eventDTO.getEventID());
        return "redirect:/events";
    }

    @RequestMapping(value = "/deleteContactFromEvent", method = RequestMethod.POST)
    public String deleteContactFromEvent(EventDTO eventDTO, Model model) {
        Event event = eventService.findByEventID(eventDTO.getEventID());
        Set<Contact> contacts = event.getContacts();
        System.out.println(contacts.size());
        Contact deleteContact = contactService.findByContactID(eventDTO.getContactTempID());
        contacts.remove(deleteContact);
        System.out.println(contacts.size());
        event.setContacts(contacts);
        eventService.saveEvent(event);
        eventDTO.setEvent(event);
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("viewTable", checkTables(event));
        return "events/eventDetails";
    }

    private boolean checkTables(Event event){
        if(event.getContacts().size()>0){
            return true;
        }
        return false;
    }





}
