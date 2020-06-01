package de.hohenheim.sopraproject.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer eventId;

    private Date date ;

    private String place;

    private String eventName;

    //private String time;

    private String text;

    @ManyToMany
    private Set<Contact> contacts;

    @ManyToMany(mappedBy = "events")
    private Set<User> users = new HashSet<>();

    //Sergej
    //@ManyToMany
    //private List<Role> role = new ArrayList<Role>();

    public Event() {
        //empty constructor for Hibernate
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
     */

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

    /**
    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
     */

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
}
