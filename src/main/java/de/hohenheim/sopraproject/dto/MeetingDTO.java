package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Meeting;

import java.util.LinkedList;
import java.util.List;

public class MeetingDTO {

    public Integer meetingID;
    private Meeting meeting = new Meeting();
    private List<Meeting> allMeetings = new LinkedList<>();
    private List<Meeting> allUpcomingMeetings = new LinkedList<>();
    private List<Contact> foundMeetings = new LinkedList<>();
    private String searchWord = "";
    private List<Contact> chosenMeetings = new LinkedList<>();
    public Contact contact = new Contact();
    public Integer contactTempID;

    public MeetingDTO() {
    }

    public List<Meeting> getAllUpcomingMeetings() {
        return allUpcomingMeetings;
    }

    public void setAllUpcomingMeetings(List<Meeting> allUpcomingMeetings) {
        this.allUpcomingMeetings = allUpcomingMeetings;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public List<Meeting> getAllMeetings() {
        return allMeetings;
    }

    public void setAllMeetings(List<Meeting> allMeetings) {
        this.allMeetings = allMeetings;
    }

    public List<Contact> getFoundMeetings() {
        return foundMeetings;
    }

    public void setFoundMeetings(List<Contact> foundMeetings) {
        this.foundMeetings = foundMeetings;
    }

    public List<Contact> getChosenMeetings() {
        return chosenMeetings;
    }

    public void setChosenMeetings(List<Contact> chosenMeetings) {
        this.chosenMeetings = chosenMeetings;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Integer getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(Integer meetingID) {
        this.meetingID = meetingID;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Integer getContactTempID() {
        return contactTempID;
    }

    public void setContactTempID(Integer contactTempID) {
        this.contactTempID = contactTempID;
    }
}
