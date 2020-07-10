
package de.hohenheim.sopraproject.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * This class defines all attributes which are needed to create n event. It has a primary key which is the eventID,
 * and some more attributes. It also has a many to many relationship with contacts, which means that contacts and events
 * are related to each other with both of their primary keys in a separately table. It also goes with a many to many
 * relationship with the users. The attribute names are the names of the columns at the database table of the event.
 * Event ID is the primary key.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer eventID;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Embedded
    private Address address;

    private String eventName;

    private String text;

    @OneToMany
    private Set<ContactHistory> contactHistories = new HashSet<>();

    @ManyToMany
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany (mappedBy = "events", cascade = CascadeType.ALL)
    private List<Tags> tags = new LinkedList<>();

    public Event() {
        //empty constructor for Hibernate
    }

    /**
     * The constructor which calls the setter methods of the specified attributes, to ensure that all the defensive
     * programming which were made in the special setter methods has its validity, even if the attributes are called in
     * the constructor. This ensures that only attributes are set to the constructor which requires the conditions.
     * This is only for necessary for attributes with expanded setter.
     * @param eventID
     * @param date
     * @param address
     * @param eventName
     * @param text
     * @param contactsEvent
     * @param year
     * @param month
     * @param day
     * @param contacts
     */
    public Event(Integer eventID, LocalDate date, Address address, String eventName, String text, Set<Contact> contactsEvent,
                 int year, int month, int day, Set<Contact> contacts) {
        this.eventID = eventID;
        this.date = date;
        this.address = address;
        this.eventName = eventName;
        this.text = text;
        this.contacts = contacts;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getEventId() {
        return eventID;
    }

    public void setEventId(Integer eventID) {
        this.eventID = eventID;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    /**
     * Sets the date of the event. It takes  in three values: int year, int month and int day and the output
     * is the following date-format: YYYY-MM-DD. If the month or the day value only have one digit, it adds a zero before
     * this digit. The method also checks if 0 < day < 31, 0 < month < 12 and year > 0. If the input does not fit the
     * formatting rules, an Exception will be thrown.
     *
     */

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addEventContact(Contact contact){
        if(contact != null){
            contacts.add(contact);
        }
    }

    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);
    }

    public void setDate(LocalDate date) {
        this.date = date;

    }

    public String getSearchString(){
        return eventName + text;
    }

    public Set<ContactHistory> getContactHistories() {
        return contactHistories;
    }

    public void setContactHistories(Set<ContactHistory> contactHistories) {
        this.contactHistories = contactHistories;
    }
}
