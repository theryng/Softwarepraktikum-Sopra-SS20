
package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class defines all attributes which are needed to create n event. It has a primary key which is the eventID,
 * and some more attributes. It also has a many to many relationship with contacts, which means that contacts and events
 * are related to each other with both of their primary keys in a separately table. It also goes with a many to many
 * relationship with the users. The attribute names are the names of the columns at the database table of the event.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer eventId;

    private String date ;

    @Embedded
    private Address address;

    private String eventName;

    private String text;

    @ManyToMany
    private Set<Contact> contactsEvent = new HashSet<>();

    @ManyToMany(mappedBy = "events")
    private Set<User> users = new HashSet<>();

    public Event() {
        //empty constructor for Hibernate
    }

    public Event(Integer eventId, String date, Address address, String eventName, String text, Set<Contact> contactsEvent,
                 Set<User> users) {
        this.eventId = eventId;
        this.date = date;
        this.address = address;
        this.eventName = eventName;
        this.text = text;
        this.contactsEvent = contactsEvent;
        this.users = users;
    }

    public Set<Contact> getContactsEvent() {
        return contactsEvent;
    }

    public void setContactsEvent(Set<Contact> contactsEvent) {
        this.contactsEvent = contactsEvent;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Integer getEventid() {
        return eventId;
    }

    public void setEventid(Integer eventid) {
        this.eventId = eventid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int year, int month, int day) {

        String stringOfYear = Integer.toString(year);
        String stringOfMonth = Integer.toString(month);
        String stringOfDay = Integer.toString(day);

        if(stringOfMonth.length() == 1){
            stringOfMonth = "0" + stringOfMonth;
        }

        if(stringOfDay.length() == 1){
            stringOfDay = "0" + stringOfDay;
        }

        if(day > 31 || day < 1 || month > 12 || month < 1 || year < 0){
            throw new IllegalStateException("Illegal state of year, month or day");
        }
        if(stringOfYear.length() == 4  &&
                stringOfMonth.length() == 2 &&
                stringOfDay.length() == 2) {

            this.date = stringOfYear + "-" + stringOfMonth + "-" + stringOfDay;

        } else {
            throw new IllegalStateException("Date has to be in this format: yyyy-MM-dd");
        }

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
        //setDate(dateOfEvent);
    }

    public Date convertStringToDate(final String string){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try{
            return format.parse(string);
        } catch(Exception exception){
            return null;
        }
    }

    public void addEventContact(Contact contact){
        if(contact != null){
            contactsEvent.add(contact);
        }
    }
}
