package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class Tags {

    @Id
    @GeneratedValue
    private Integer tagsID;

    private String name;
    @ManyToMany
    private Set<Contact> contacts = new HashSet<>();
    @ManyToMany
    private Set<Institute> institutes = new HashSet<>();
    @ManyToMany
    private Set<Event> events = new HashSet<>();

    @ManyToMany
    private Set<Project> projects = new HashSet<>();

    public Tags() {
    }

    public Integer getTagsID() {
        return tagsID;
    }

    public void setTagsID(Integer tagsID) { this.tagsID = tagsID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(Set<Institute> institutes) {
        this.institutes = institutes;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getSearchString(){
        return name;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
