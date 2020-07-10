package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Occurrence;

import java.util.LinkedList;
import java.util.List;

public class OccurrenceDTO {

    private Occurrence occurrence = new Occurrence();
    private List<Occurrence> allOccurrences = new LinkedList<>();
    private List<Occurrence> allUpcomingOccurrences = new LinkedList<>();
    private List<Contact> foundOccurrences = new LinkedList<>();
    private List<Contact> chosenOccurrences = new LinkedList<>();
    private String searchWord = "";
    public Integer occurrenceID;
    public Contact contact = new Contact();
    public Integer contactTempID;

    public OccurrenceDTO() {
    }

    public List<Occurrence> getAllUpcomingOccurrences() {
        return allUpcomingOccurrences;
    }

    public void setAllUpcomingOccurrences(List<Occurrence> allUpcomingOccurrences) {
        this.allUpcomingOccurrences = allUpcomingOccurrences;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }

    public List<Occurrence> getAllOccurrences() {
        return allOccurrences;
    }

    public void setAllOccurrences(List<Occurrence> allOccurrences) {
        this.allOccurrences = allOccurrences;
    }

    public List<Contact> getFoundOccurrences() {
        return foundOccurrences;
    }

    public void setFoundOccurrences(List<Contact> foundOccurrences) {
        this.foundOccurrences = foundOccurrences;
    }

    public List<Contact> getChosenOccurrences() {
        return chosenOccurrences;
    }

    public void setChosenOccurrences(List<Contact> chosenOccurrences) {
        this.chosenOccurrences = chosenOccurrences;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Integer getOccurrenceID() {
        return occurrenceID;
    }

    public void setOccurrenceID(Integer occurrenceID) {
        this.occurrenceID = occurrenceID;
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
