package de.hohenheim.sopraproject.controller.events;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.EditingHistoryService;
import de.hohenheim.sopraproject.service.EventService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * This controller contains the methods to create a new event
 *
 * This controller contains all important methods to create a new event and saving it inside the database.
 * It allows to get all informations about an event, to save a newly created event and to look up all the details
 * about a certain event.

 */
@Controller
public class EventsController {

    @Autowired
    private EditingHistoryService editingHistoryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private TagsService tagsService;

    /**
     * This method gets all the information about an event
     *
     * This method gives out all the information about the events stored inside the database
     *
     * @return events
     */
    @RequestMapping(value ="/events", method = RequestMethod.GET)
    public String events(Model model) {
        List<Event> allEvents = eventService.findAllEvents();
        EventDTO eventDTO = new EventDTO();
        eventDTO.setAllEvents(allEvents);
        eventDTO.setEvent(new Event());
        String searchword = "";
        model.addAttribute("searchWord", searchword);
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        return "events";
    }

    /**
     * This method saves a new event
     *
     * This method saves a newly created event to the database. It also merges the temporary attributes of ZipCode, City,
     * Street and houseNumber to one single attribute called address. Once the new event was saved to the database the page will
     * be reloaded and the table will be updated. The new event will no show up on the page events.
     *
     * @return events
     */
    @RequestMapping(value="/saveEvent", method = RequestMethod.POST)
    public String saveEvent(@Valid EventDTO eventDTO, BindingResult result, Model model, Principal principal){
        if(result.hasErrors()) {

            model.addAttribute("allEvents", eventService.findAllEvents());

            return "events";

        }
        else {
                eventService.saveEvent(eventDTO.getEvent());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();


                editingHistoryService.saveEditingHistory(new EditingHistory(principal.getName(), "Event: " + eventDTO.getEvent().getEventName(), dateFormat.format(date)));
                return "redirect:/events";
            }
    }

    /**
     *  Method which can be used to search for a certain event.
     *  Calls the Event Finder, and uses a searchWord to find an event.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return events
     */
    @RequestMapping(value ="/searchEvent", method = RequestMethod.POST)
    public String searchEvents(String searchWord, Model model) {
        List<Event> allContacts;
        ContactFinder findContact = new ContactFinder();

        LinkedList<Event> foundContactsTemp = findContact.findEvents(searchWord, eventService.findAllEvents());

        allContacts = foundContactsTemp;

        boolean showList = false;
        if(allContacts.size()>0){
            showList = true;
        }
        EventDTO eventDTO = new EventDTO();
        eventDTO.setAllEvents(foundContactsTemp);
        model.addAttribute("searchWord", "");
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        return "events";
    }

    /**
     * This method can sort an Event by tag.
     *
     * @param tag
     * @param model
     * @return events
     */
    @PostMapping(value ="/sortByTagEvents")
    public String sortByTag(Tags tag, Model model) {

        Tags tags = tagsService.findByTagID(tag.getTagsID());
        List<Event> allEvents = eventService.findAllEvents();

        List<Event> foundEvents = new LinkedList<Event>();
        for(Event event : allEvents){
            if(event.getTags().contains(tags)){
            foundEvents.add(event);
            }
        }
        allEvents = foundEvents;
        boolean showList = false;
        if(allEvents.size()>0){
            showList = true;
        }
        EventDTO eventDTO = new EventDTO();
        eventDTO.setAllEvents(allEvents);
        eventDTO.setEvent(new Event());
        String searchword = "";
        model.addAttribute("searchWord", searchword);
        model.addAttribute("eventDTO", eventDTO);
        model.addAttribute("allTags", tagsService.findAllTags());
        model.addAttribute("tag", new Tags());
        return "events";
    }

}
