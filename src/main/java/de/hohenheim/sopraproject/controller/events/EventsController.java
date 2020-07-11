package de.hohenheim.sopraproject.controller.events;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.entity.*;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.EventService;
import de.hohenheim.sopraproject.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@Controller
public class EventsController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TagsService tagsService;


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


    @RequestMapping(value="/saveEvent", method = RequestMethod.POST)
    public String saveEvent(@Valid EventDTO eventDTO, BindingResult result, Model model){
        if(result.hasErrors()) {
            System.out.println("Fehler");
            model.addAttribute("allEvents", eventService.findAllEvents());

            return "events";

        }
        else {

            eventService.saveEvent(eventDTO.getEvent());
        }
            return "redirect:/events";
    }


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

    @PostMapping(value ="/sortByTagEvents")
    public String sortByTag(Tags tag, Model model) {
        System.out.println("sorting by Tag");
        Tags tags = tagsService.findByTagID(tag.getTagsID());
        List<Event> allEvents = eventService.findAllEvents();
        System.out.println(tag.getName() + tag.getTagsID());
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
