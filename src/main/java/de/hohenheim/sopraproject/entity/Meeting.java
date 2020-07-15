package de.hohenheim.sopraproject.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines all the meetings that the admin has
 */
@Entity
public class Meeting {

    @Id
    @GeneratedValue
    private Integer meetingID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String description;

    private LocalTime startTime;

    private LocalTime endTime;

    private String title;

    @OneToMany
    private Set<Contact> contacts = new HashSet<>();

    public Meeting(){
        //for hibernate
    }

    public Integer getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(Integer meetingID) {
        this.meetingID = meetingID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setStartTime(int hours, int minutes) {
        if(hours < 25 && minutes < 60) {
            startTime = LocalTime.of(hours, minutes);
        } else {
            throw new IllegalStateException();
        }
    }

    public void setEndTime(int hours, int minutes) {
        if(hours < 25 && minutes < 60) {
            endTime = LocalTime.of(hours, minutes);
        } else {
            throw new IllegalStateException();
        }
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact){
        if(contact != null && contacts != null){
            contacts.add(contact);
        }
        else{
            throw new IllegalStateException();
        }
    }
}
