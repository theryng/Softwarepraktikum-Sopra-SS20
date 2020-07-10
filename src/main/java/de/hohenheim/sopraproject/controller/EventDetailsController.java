package de.hohenheim.sopraproject.controller;

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

    @GetMapping(value = "/eventDetails/{eventID}")
    public String eventDetails(@PathVariable("eventID") Integer eventID, Model model) {
        System.out.println("Testing the stuff " + eventID);
        EventDTO eventDTO = new EventDTO();
        Event event = eventService.findByEventID(eventID);
        System.out.println("Anzahl Tags: " + event.getTags().size());
        String searchWord = "";
        TagsDTO tagsDTO = new TagsDTO();
        tagsDTO.setOriginalID(eventID);
        System.out.println(event.getContacts().size());
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
     * This method saves the Institutes and saves it to the existing database
     *
     * It also checks if the given ID is already mapped to a different
     * existing institute. As long thats not the case a new institute will be saved to the database. Once the institute is saved
     * the page will be reloaded to update the table with the new given information/attributes.
     *
     * @param
     * @return redirect:/institutes
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

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
    public String deleteEvent(EventDTO eventDTO, Model model) {
        eventService.deleteByEventID(eventDTO.getEventID());
        model.addAttribute("eventDTO", eventDTO);
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
        return "redirect:/eventDetails/"+eventDTO.getEventID();
    }

    @GetMapping("/deleteEventTag")
    public String deleteEventTag(TagsDTO tagsDTO) {
        List<Tags> tags = eventService.findByEventID(tagsDTO.getOriginalID()).getTags();
        System.out.println("Number of Tags 1" + tags.size());
        Tags removeTag = new Tags();
        for(Tags tag : tags){
            if(tag.getTagsID() == tagsDTO.getTagID()){
                System.out.println("remove");
                removeTag = tag;
            }
        }
        tags.remove(removeTag);

        System.out.println("Number of Tags 2" + tags.size());
        Event event = eventService.findByEventID(tagsDTO.getOriginalID());
        event.setTags(tags);
        System.out.println(event.getTags().size());
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
