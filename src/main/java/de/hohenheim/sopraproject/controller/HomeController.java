package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.MeetingDTO;
import de.hohenheim.sopraproject.entity.Meeting;
import de.hohenheim.sopraproject.repository.EditingHistoryRepository;
import de.hohenheim.sopraproject.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private EditingHistoryRepository editingHistoryRepository;

    @Autowired
    private MeetingService meetingService;

    /**
     * Zeigt die Startseite Ihrer Anwendung.
     * @param model enthält alle ModelAttribute.
     * @return home-Seite.
     */
    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("message", "Und hier sehen Sie ein Model Attribut");
        model.addAttribute("editingHistory", editingHistoryRepository.findAll());

        List<Meeting> allUpcomingMeetings = meetingService.findtAllUpcomingMeetings();
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setAllUpcomingMeetings(allUpcomingMeetings);
        meetingDTO.setMeeting(new Meeting());
        model.addAttribute("meetingDTO", meetingDTO);

        return "home";
    }

    /**
     * This method saves a new meeting
     *
     * This method saves a newly created meeting to the database. Once the new meeting was saved to the database the page will
     * be reloaded and the table will be updated.
     *
     * @param
     * @return redirect:/home
     */
    @RequestMapping(value="/saveMeeting", method = RequestMethod.POST)
    public String saveMeeting(@Valid MeetingDTO meetingDTO, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Fehler");
        }
        else{
            meetingService.saveMeeting(meetingDTO.getMeeting());
        }
        return "redirect:/home";
    }

    /**
     * This method finds all meeting.
     *
     * This method finds all existing meeting and returns them to the user
     *
     * @param model
     * @return home
     */
    @RequestMapping("/allMeetings")
    public String allmeetings(Model model) {
        model.addAttribute("allMeetings", meetingService.findAllMeetings());
        return "home";
    }


    /**
     *  Method which can be used to search for a certain meeting.
     *  Calls the Contact Finder, and uses a searchWord to find a meeting.
     *  Reloads the Site at the very End.
     * @param searchWord
     * @return redirect:/contacts
     */
    @RequestMapping(value ="/searchMeeting", method = RequestMethod.POST)
    public String searchMeetings(String searchWord) {
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/deleteMeeting", method = RequestMethod.GET)
    public String deleteMeeting(MeetingDTO meetingDTO) {
        meetingService.deleteByMeetingID(meetingDTO.getMeetingID());
        return "redirect:/home";
    }
}