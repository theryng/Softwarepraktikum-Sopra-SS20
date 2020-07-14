package de.hohenheim.sopraproject.controller.events;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.dto.TagsDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.repository.ContactRepository;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
/**
 * This controller is used to handle all methods revolving around the html page eventDetails
 *
 * It helps with various method to save new events, delete existing ones, create a membership with a Contact
 * and delete this membership
 *
 */
@Controller
public class EventDetailsController {



    @Autowired
    private EventService eventService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Main method for Viewing of Event Details Site, adds necessary Attributes
     * @param model
     * @return eventDetails
     */
    @GetMapping(value = "/eventDetails/{eventID}")
    public String eventDetails(@PathVariable("eventID") Integer eventID, Model model) {

        EventDTO eventDTO = new EventDTO();
        Event event = eventService.findByEventID(eventID);

        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(eventID);

        eventDTO.setEvent(event);
        eventDTO.setEventID(event.getEventID());
        model.addAttribute("allContacts", event.getContacts());
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("event", event);
        model.addAttribute("viewTable", true);
        model.addAttribute("tagDTO", tagsDTO);
        return "events/eventDetails";
    }

    /**
     * This method saves the Events and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different existing event.
     * As long thats not the case a new event will be saved to the database. Once the event is saved
     * the page will be reloaded to update the table.
     *
     * @param event
     * @return redirect:/events
     */
    @RequestMapping(value = "/savingEvent", method = RequestMethod.POST)
    public String savingEvent(@Valid Event event, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("event", event);
            EventDTO eventDTO = new EventDTO();
            Event tempEvent = eventService.findByEventID(event.getEventID());
            tempEvent.setAddress(event.getAddress());
            tempEvent.setDate(event.getDate());
            tempEvent.setEventName(event.getEventName());
            tempEvent.setText(event.getText());
            eventDTO.setEvent(tempEvent);
            eventDTO.setEventID(event.getEventID());
            model.addAttribute("allContacts", tempEvent.getContacts());
            model.addAttribute("eventDTO", eventDTO);
            model.addAttribute("event", tempEvent);
            model.addAttribute("viewTable", true);
            return "events/eventDetails";
        }
        else{
            if(!eventService.findByEventID(event.getEventID()).equals(event)){
                Event tempEvent = eventService.findByEventID(event.getEventID());
                tempEvent.setAddress(event.getAddress());
                tempEvent.setDate(event.getDate());
                tempEvent.setEventName(event.getEventName());
                tempEvent.setText(event.getText());
                eventService.saveEvent(tempEvent);
            }
            return "redirect:/eventDetails/"+event.getEventID();
        }
    }

    /**
     * This method deletes an existing event inside the database
     *
     * An existing event will be deleted. The corresponding eventID will also be deleted so new events can get this
     * ID in the future. Once the event is deleted the page will be reloaded to update the event table.
     *
     * @param model
     * @return redirect:/events
     */
    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteEvent(EventDTO eventDTO, Model model) {
        eventService.deleteByEventID(eventDTO.getEventID());
        model.addAttribute("eventDTO", eventDTO);
        return "redirect:/events";
    }

    /**
     * This method deletes a Contact from an existing event.
     *
     * @param eventDTO
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteContactFromEvent", method = RequestMethod.POST)
    public String deleteContactFromEvent(EventDTO eventDTO, Model model) {
        Event event = eventService.findByEventID(eventDTO.getEventID());
        Set<Contact> contacts = event.getContacts();

        Contact deleteContact = contactService.findByContactID(eventDTO.getContactTempID());
        contacts.remove(deleteContact);

        event.setContacts(contacts);
        eventService.saveEvent(event);
        eventDTO.setEvent(event);
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("viewTable", checkTables(event));
        return "redirect:/eventDetails/"+eventDTO.getEventID();
    }

    /**
     * This method deletes the tag attached to an Event.
     * @param tagsDTO
     * @return
     */
    @GetMapping("/deleteEventTag")
    public String deleteEventTag(TagsDTO tagsDTO) {
        List<Tags> tags = eventService.findByEventID(tagsDTO.getOriginalID()).getTags();

        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){

                removeTag = tag;
            }
        }
        tags.remove(removeTag);


        Event event = eventService.findByEventID(tagsDTO.getOriginalID());
        event.setTags(tags);

        eventService.saveEvent(event);
        Tags tag = tagsService.findByTagID(removeTag.getTagsID());
        tag.getEvents().remove(event);
        tagsService.saveTags(tag);
        return "redirect:/eventDetails/"+event.getEventID();
    }


    private boolean checkTables(Event event){
        if(event.getContacts().size()>0){
            return true;
        }
        return false;
    }





}
