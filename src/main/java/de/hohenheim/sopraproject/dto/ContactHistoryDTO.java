package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.ContactHistory;
import de.hohenheim.sopraproject.entity.Event;
import de.hohenheim.sopraproject.entity.Project;

import java.util.LinkedList;
import java.util.List;

public class ContactHistoryDTO {
    public ContactHistory contactHistory = new ContactHistory();
    public List<Contact> foundContacts= new LinkedList<Contact>();
    public List<Contact> chosenContacts = new LinkedList<Contact>();
    public List<Event> allEvents = new LinkedList<Event>();
    public List<Project> allProjects = new LinkedList<Project>();
    public String searchWord = "";
    public int selectedContact;
    public List<Integer> chosenIDs = new LinkedList<Integer>();
    public String stringChosenIDs = "";
    public String stringFoundIDs = "";
    public String originalContactID;
    public Integer originalContactHistoryID;
    public String eventID = "";
    public ConnectedObject connectedObject;
    public Integer connectedID;

    enum ConnectedObject{
        Event,
        Project
    }


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

    public ConnectedObject getConnectedObject() {
        return connectedObject;
    }

    public void setConnectedObject(ConnectedObject connectedObject) {
        this.connectedObject = connectedObject;
    }

    public Integer getConnectedID() {
        return connectedID;
    }

    public void setConnectedID(Integer connectedID) {
        this.connectedID = connectedID;
    }
}
