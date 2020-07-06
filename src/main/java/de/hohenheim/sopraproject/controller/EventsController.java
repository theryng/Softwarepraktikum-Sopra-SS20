package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.dto.InstituteDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.EditingHistory;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Institute;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class EventsController {

    @Autowired
    private EventService eventService;


    @RequestMapping(value ="/events", method = RequestMethod.GET)
    public String events(Model model) {
        List<Event> allEvents = eventService.findAllEvents();
        EventDTO eventDTO = new EventDTO();
        eventDTO.setAllEvents(allEvents);
        eventDTO.setEvent(new Event());
        model.addAttribute("eventDTO", eventDTO);
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
    public String searchEvents(String searchWord) {

        return "redirect:/events";
    }


    @RequestMapping("/allEvents")
    public String allEvents(Model model) {
        model.addAttribute("allEvents", eventService.findAllEvents());
        return "events";
    }

}
