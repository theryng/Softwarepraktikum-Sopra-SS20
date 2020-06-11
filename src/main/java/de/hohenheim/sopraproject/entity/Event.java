
package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class defines all attriobutes which are needed to create an event. It has an primary key which is the eventID,
 * and some more attributes. It also has a many to many relationship with contacts, which means that contacts and events
 * are related to each other with both of their primary keys in a separately table. it also goes with a many to many
 * relationship with the users. The attribute names are the names of the columns at the database table of the event.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer eventId;

    private Date date ;

    @Embedded
    private Address address;

    private String eventName;

    private String text;

    @ManyToMany (mappedBy = "events")
    private Set<Contact> contacts = new HashSet<>();

    public Event() {
        //empty constructor for Hibernate
    }

    public Event(Integer eventId, Date date, Address address, String eventName, String text, Set<Contact> contacts,
                 Set<User> users) {
        this.eventId = eventId;
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


    public Integer getEventid() {
        return eventId;
    }

    public void setEventid(Integer eventid) {
        this.eventId = eventid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setFormatDateOfEvent(int year, int month, int day){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();

        String stringDate = format.format(date);
        Date dateOfEvent = convertStringToDate(stringDate);
        setDate(dateOfEvent);
    }

    public Date convertStringToDate(final String string){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try{
            return format.parse(string);
        } catch(Exception exception){
            return null;
        }
    }
    public void addParticipent (Contact contact){
        contacts.add(contact);
    }
}
