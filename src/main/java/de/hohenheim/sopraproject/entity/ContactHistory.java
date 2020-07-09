package de.hohenheim.sopraproject.entity;

import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines all attributes that are necessary to define a Contacthistory. There is one Contacthistory for
 * every existing Contact. The primary key is "contacthistoryId" and it has a many to many relation to "Contact".
 * Contacthistories contain a free text, a date and the Contact related to the Contacthistory. Contactshistory ID is the primary key.
 * @Author Sergej Bensack
 */
@Entity
public class ContactHistory {

    @Id
    @GeneratedValue
    private Integer contactHistoryID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public String text;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Contact> contactOfHistory = new HashSet<>();

    public ContactHistory(Integer contactHistoryID, String date, String text, Set<Contact> contactOfHistory, int year,
                          int month, int day) {
        this.contactHistoryID = contactHistoryID;
        setDate(LocalDate.of(year, month, day));
        this.text = text;
        this.contactOfHistory = contactOfHistory;
    }

    public ContactHistory() {
        //empty constructor for Hibernate
    }

    public Set<Contact> getContactOfHistory() {
        return contactOfHistory;
    }

    public void setContactOfHistory(Set<Contact> contactOfHistory) {
        this.contactOfHistory = contactOfHistory;
    }

    public void addContactHistoryContact(Contact contact){
        if(contact != null){
            contactOfHistory.add(contact);
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getContactHistoryID() {
        return contactHistoryID;
    }

    public void setContactHistoryID(Integer contacthistoryId) {
        this.contactHistoryID = contacthistoryId;
    }


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
