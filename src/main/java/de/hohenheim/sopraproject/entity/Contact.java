package de.hohenheim.sopraproject.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class defines all attributes which are needed to create an contact. It has an primary key which is the contactID,
 * and some more attributes. It also has a many to many relationship with events, which means that contacts and events
 * are related to each other with both of their primary keys in a separately table. it also goes with a many to one
 * relationship with the institute. That means that one Contact is related to one (primary) institute. The attribute
 * names are the names of the columns at the database table of the contact.
 * By Mark
 */
@Entity
public class Contact {


    @Id
    @GeneratedValue
    private Integer contactID;

    private String firstname;

    private String lastname;

    private String occupation;

    private String email;

    private String courseOfStudies;

    private String freeText;

    private String dayOfBirth;

    @Embedded
    private Address address;
    @Transient
    private String tempZipCode;
    @Transient
    private String tempHouseNmbr;
    @Transient
    private String tempCity;
    @Transient
    private String tempStreet;

    private String hobby;

    private String linkToHomepage;

    @OneToMany(mappedBy = "contactA", cascade = CascadeType.ALL)
    private Set<Relationship> relationshipsA = new HashSet<>();

    @OneToMany(mappedBy = "contactB", cascade = CascadeType.ALL)
    private Set<Relationship> relationshipsB = new HashSet<>();

//    @OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL)
//    private Set<Relationship> relationships = new HashSet<>();

    @ManyToMany(mappedBy = "contacts")
    private Set<Institute> institutes = new HashSet<>();

    @ManyToMany(mappedBy = "contactsEvent")
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Contacthistory> contacthistories;




    public Contact(String firstname, String lastname, String occupation, String email,
                   String courseOfStudies, String freeText, String dayOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.occupation = occupation;
        this.email = email;
        this.courseOfStudies = courseOfStudies;
        this.freeText = freeText;
        this.dayOfBirth = dayOfBirth;
    }

    public Contact() {
        //empty constructor for Hibernate
    }

    public Date convertStringToDate(final String string){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try{
            return format.parse(string);
        } catch(Exception exception){
            return null;
        }
    }

    public void setFormatDateOfBirth(int year, int month, int day){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Date date = calendar.getTime();

        String stringDate = format.format(date);
        Date dayOfBirth = convertStringToDate(stringDate);
        setDayOfBirth(year+"-"+month+"-"+day);
    }

    public Set<Contacthistory> getContacthistories() {
        return contacthistories;
    }

    public void setContacthistories(Set<Contacthistory> contacthistories) {
        this.contacthistories = contacthistories;
    }

    public Integer getContactID() {
        return contactID;
    }

   public void setContactID(Integer contactID) {
       this.contactID = contactID;
   }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseOfStudies() {
        return courseOfStudies;
    }

    public void setCourseOfStudies(String courseOfStudies) {
        this.courseOfStudies = courseOfStudies;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getLinkToHomepage() {
        return linkToHomepage;
    }

    public void setLinkToHomepage(String linkToHomepage) {
        this.linkToHomepage = linkToHomepage;
    }

    public Set<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(Set<Institute> institutes) {
        this.institutes = institutes;
    }

    public String getTempZipCode() {
        return tempZipCode;
    }

    public void setTempZipCode(String tempZipCode) {
        this.tempZipCode = tempZipCode;
    }

    public String getTempHouseNmbr() {
        return tempHouseNmbr;
    }

    public void setTempHouseNmbr(String tempHouseNmbr) {
        this.tempHouseNmbr = tempHouseNmbr;
    }

    public String getTempCity() {
        return tempCity;
    }

    public void setTempCity(String tempCity) {
        this.tempCity = tempCity;
    }

    public String getTempStreet() {
        return tempStreet;
    }

    public void setTempStreet(String tempStreet) {
        this.tempStreet = tempStreet;
    }
}
