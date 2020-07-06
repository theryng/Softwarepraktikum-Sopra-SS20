package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Tags {

    @Id
    @GeneratedValue
    private int tagsID;

    private String name;
    @ManyToMany
    private List<Contact> contacts = new LinkedList<>();
    @ManyToMany
    private List<Institute> institutes = new LinkedList<>();
    @ManyToMany
    private List<Event> events = new LinkedList<>();

    public Tags() {
    }

    public int getTagsID() {
        return tagsID;
    }

    public void setTagsID(int tagsID) {
        tagsID = tagsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(List<Institute> institutes) {
        this.institutes = institutes;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getSearchString(){
        return name;
    }
}
