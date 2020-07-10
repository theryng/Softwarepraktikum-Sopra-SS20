package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.ContactHistoryDTO;
import de.hohenheim.sopraproject.dto.EventDTO;
import de.hohenheim.sopraproject.dto.RelationshipDTO;
import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Relationship;
import de.hohenheim.sopraproject.service.ContactFinder;
import de.hohenheim.sopraproject.service.ContactHistoryService;
import de.hohenheim.sopraproject.service.ContactService;
import de.hohenheim.sopraproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

/**
 * Controller for the First Step of the Relationship Creation process
 * @date 26.06.2020
 * @author Lukas Januschke
 */
@Controller
public class EventSelectorController {

    @Autowired
    private ContactHistoryService contactHistoryService;

    @Autowired
    private EventService eventService;

    /**
     * Main method of the Relationship Creator
     * Also adds necessary Attributes
     * @param model
     * @return relationshipCreator1
     */
    @GetMapping("/eventSelector/{contactHistoryID}/{contactID}")
    public String EventSelectorController(@PathVariable("contactID") Integer contactID, @PathVariable("contactHistoryID") Integer contactHistoryID, Model model) {
        ContactHistoryDTO contactHistoryDTO = new ContactHistoryDTO();
        contactHistoryDTO.setOriginalContactID(contactID+"");
        contactHistoryDTO.setOriginalContactHistoryID(contactHistoryID);
        model.addAttribute("contactHistoryDTO", contactHistoryDTO);
        model.addAttribute("viewTable", false);

        return "contacts/eventSelector";
    }

    /**
     * searches for a Contact to have a relationship with
     * uses the findContacts method of the ContactFinder class
     * @param contactHistoryDTO
     * @return relationshipCreator1
     */
    @RequestMapping(value ="/searchEventForHistory", method = RequestMethod.POST)
    public String searchEvent(ContactHistoryDTO contactHistoryDTO, Model model) {
        System.out.println(contactHistoryDTO.getSearchWord());
        String searchWord = contactHistoryDTO.getSearchWord();
        ContactFinder findContact = new ContactFinder();
        List<Event> foundEventsTemp = findContact.findEvents(searchWord, eventService.findAllEvents());
        contactHistoryDTO.setAllEvents(foundEventsTemp);

        model.addAttribute("viewTable", true);
        model.addAttribute("relationshipDTO", contactHistoryDTO);
        return "contacts/eventSelector";
    }

    /**
     * Sets the Contact to have a Relationship with
     * @return relationshipCreator2
     */
    @RequestMapping(value = "/selectEventForHistory", method = RequestMethod.POST)
    public String setContactB(ContactHistoryDTO contactHistoryDTO, RedirectAttributes redirectAttributes) {
        ContactHistory contactHistory = contactHistoryService.findByContactHistoryID(contactHistoryDTO.getOriginalContactHistoryID());
        Event event = eventService.findByEventID(Integer.valueOf(contactHistoryDTO.getEventID()));
        event.getContactHistories().add(contactHistory);
        contactHistory.setEvent(event);
        contactHistoryService.saveContacthistory(contactHistory);
        eventService.saveEvent(event);
        return "redirect:/contactHistoryEditor/"+contactHistoryDTO.getOriginalContactID()+"/"+contactHistoryDTO.getOriginalContactHistoryID();
    }
}