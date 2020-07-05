package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Event;

import java.util.LinkedList;
import java.util.List;

public class EventDTO {

    public Event event = new Event();
    private List<Event> allEvents = new LinkedList<>();
    private List<Contact> foundEvents = new LinkedList<>();
    private List<Contact> chosenEvents = new LinkedList<>();
    private String searchWord = "";
    public Integer eventID;
    public Contact contact = new Contact();
    public Integer contactTempID;

    public EventDTO() {

    }



    public List<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public Integer getContactTempID() {
        return contactTempID;
    }
    public void setContactTempID(Integer contactTempID) {
        this.contactTempID = contactTempID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event institute) {
        this.event = event;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }


    public List<Contact> getFoundEvents() {
        return foundEvents;
    }

    public void setFoundEvents(List<Contact> foundEvents) {
        this.foundEvents = foundEvents;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Contact> getChosenEvents() {
        return chosenEvents;
    }

    public void setChosenEvents(List<Contact> chosenEvents) {
        this.chosenEvents = chosenEvents;
    }

}
