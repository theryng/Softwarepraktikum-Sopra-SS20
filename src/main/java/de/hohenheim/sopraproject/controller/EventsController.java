package de.hohenheim.sopraproject.controller;

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
        String searchword = "";
        List<Event> allEvents = eventService.findAllEvents();
        boolean showList = false;
        if(allEvents.size()>0){
            showList = true;
        }
        model.addAttribute("showList", showList);
        model.addAttribute("allEvents", allEvents);
        model.addAttribute("searchWord", searchword);
        model.addAttribute("event", new Event());

        return "event";
    }


    @RequestMapping(value="/saveEvent", method = RequestMethod.POST)
    public String saveEvent(@Valid Event event, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("Fehler");

            model.addAttribute("allEvents", eventService.findAllEvents());

            return "events";
        }
        else{
            eventService.saveEvent(event);


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            System.out.println(dateFormat.format(date));

           // editingHistoryService.saveEditingHistory(new EditingHistory("User1", "Kontakt: " + contact.getFirstname() + " " + contact.getLastname(), dateFormat.format(date)));
            return "redirect:/events";
        }



    }

    @RequestMapping(value ="/searchEvent", method = RequestMethod.POST)
    public String searchEvents(String searchWord) {

        return "redirect:/events";
    }
    @RequestMapping("/viewEvent")
    public String viewEvent(Event event) {
        EventDetailsController.eventID = event.getEventId();
        return "redirect:/eventDetails";
    }

}
