package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Project;

import java.util.LinkedList;
import java.util.List;

public class ContactHistoryDTO {
    private ContactHistory contactHistory = new ContactHistory();
    private List<Contact> foundContacts= new LinkedList<Contact>();
    private List<Contact> chosenContacts = new LinkedList<Contact>();
    private List<Event> allEvents = new LinkedList<Event>();
    private List<Project> allProjects = new LinkedList<Project>();
    private String searchWord = "";
    private int selectedContact;
    private List<Integer> chosenIDs = new LinkedList<Integer>();
    private String stringChosenIDs = "";
    private String stringFoundIDs = "";
    private String originalContactID;
    private Integer originalContactHistoryID;
    private String eventID = "";


    public ContactHistoryDTO(){
            stringChosenIDs = "";
    }

    public List<Contact> getFoundContacts() {
        return foundContacts;
    }

    public void setFoundContacts(List<Contact> foundContacts) {
        this.foundContacts = foundContacts;
    }

    public List<Contact> getChosenContacts() {
        return chosenContacts;
    }

    public void setChosenContacts(List<Contact> chosenContacts) {
        this.chosenContacts = chosenContacts;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public int getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(int selectedContact) {
        this.selectedContact = selectedContact;
    }

    public List<Integer> getChosenIDs() {
        return chosenIDs;
    }

    public void setChosenIDs(List<Integer> chosenIDs) {
        this.chosenIDs = chosenIDs;
    }

    public String getStringChosenIDs() {
        return stringChosenIDs;
    }

    public void setStringChosenIDs(String stringChosenIDs) {
        this.stringChosenIDs = stringChosenIDs;
    }

    public String getStringFoundIDs() {
        return stringFoundIDs;
    }

    public void setStringFoundIDs(String stringFoundIDs) {
        this.stringFoundIDs = stringFoundIDs;
    }

    public ContactHistory getContactHistory() {
        return contactHistory;
    }

    public void setContactHistory(ContactHistory contactHistory) {
        this.contactHistory = contactHistory;
    }

    public String getOriginalContactID() {
        return originalContactID;
    }

    public void setOriginalContactID(String originalContactID) {
        this.originalContactID = originalContactID;
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public Integer getOriginalContactHistoryID() {
        return originalContactHistoryID;
    }

    public void setOriginalContactHistoryID(Integer originalContactHistoryID) {
        this.originalContactHistoryID = originalContactHistoryID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public List<Project> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Project> allProjects) {
        this.allProjects = allProjects;
    }
}
