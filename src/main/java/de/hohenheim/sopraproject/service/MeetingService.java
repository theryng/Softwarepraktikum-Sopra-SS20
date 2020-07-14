package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Meeting;
import de.hohenheim.sopraproject.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository meetingRepository;

    public Meeting saveMeeting(Meeting meeting){
        return meetingRepository.save(meeting);
    }

    public List<Meeting> findAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting findByMeetingID(Integer id) {
        return meetingRepository.findByMeetingID(id);
    }

    public void deleteByMeetingID(Integer id) {
        meetingRepository.deleteById(id);
    }

    /**
     * gets all the upcoming meetings of the day and sorts them by time in ascending order
     */
    public List<Meeting> findtAllUpcomingMeetings(){
        List<Meeting> allMeetings = meetingRepository.findAll();
        List<Meeting> upcoming = new LinkedList<>();
        LocalDate date = LocalDate.now();

        for(Meeting meeting : allMeetings) {
            LocalDate meetingDate = meeting.getDate();
            if (meetingDate.equals(date)) {
                upcoming.add(meeting);
            }
        }
        Collections.sort(upcoming, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
        return upcoming;
    }
}
