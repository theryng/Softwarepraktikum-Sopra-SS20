package de.hohenheim.sopraproject.entity;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class defines all attributes which are needed to create an contact. It has an primary key which is the contactID,
 * and some more attributes. It also has a many to many relationship with events, which means that contacts and events
 * are related to each other with both of their primary keys in a separately table. it also goes with a many to one
 * relationship with the institute. That means that one Contact is related to one (primary) institute. The attribute
 * names are the names of the columns at the database table of the contact. It also has a many to many relation with
 * contacthistory. Thus, many Contacts can have many Contacthistories and many Contacts can be added to many
 * Contacthistories. Contact ID is the primary key.
 * @Author Mark Wagner
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dayOfBirth;

    @Transient
    private String searchString;

    @Embedded
    private Address address;

    private String hobby;

    private String linkToHomepage;

    @OneToMany(mappedBy = "contactA", cascade = CascadeType.ALL)
    public Set<Relationship> outgoingRelationships = new HashSet<>();

    @OneToMany(mappedBy = "contactB", cascade = CascadeType.ALL)
    public Set<Relationship> ingoingRelationships = new HashSet<>();

    @ManyToMany(mappedBy = "contacts", cascade =  CascadeType.ALL)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(mappedBy = "contacts", cascade = CascadeType.ALL)
    private Set<Institute> institutes = new HashSet<Institute>();

    @ManyToMany (mappedBy = "contactOfHistory", cascade = CascadeType.ALL)
    private Set<ContactHistory> contactHistory = new HashSet<>();


    public Contact(String firstname, String lastname, String occupation, String email,
                   String courseOfStudies, String freeText, LocalDate date) {

        setFirstname(firstname);
        setLastname(lastname);
        this.occupation = occupation;
        setEmail(email);
        this.courseOfStudies = courseOfStudies;
        this.freeText = freeText;
        this.dayOfBirth = date;
    }

    public Contact() {
        //empty constructor for Hibernate
    }


    public Set<ContactHistory> getContactHistory() {
        return contactHistory;
    }

    /**
     * Sets the contacthistory of a contact only if the contacthistory is initialized. Otherwise it throws an Exception.
     * @param contacthistories
     */
    public void setContacthistories(Set<ContactHistory> contacthistories) {
        if(contacthistories != null) {
            this.contactHistory = contacthistories;
        }else{
            throw new IllegalStateException("contacthistories should be initialized");
        }
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public void setDayOfBirthDate(LocalDate dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setOutgoingRelationship(Set<Relationship> outgoingRelationship) {
        this.outgoingRelationships = outgoingRelationship;
    }

    public void setIngoingRelationship(Set<Relationship> ingoingRelationship) {
        this.ingoingRelationships = ingoingRelationship;
    }

    public Set<Relationship> getOutgoingRelationships() {
        return outgoingRelationships;
    }

    public Set<Relationship> getIngoingRelationships() {
        return ingoingRelationships;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname only if it has at least 2 characters and does not contain illegal characters. Illegal characters
     * are: 0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>      Throws an Exception if there are illegal arguments.
     * @param firstname string value for the first name of a contact
     */
    public void setFirstname(String firstname) {
        Pattern pattern = Pattern.compile("[a-zA-ZäöüÄÖÜ]");
        Pattern pattern2 = Pattern.compile("[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        Matcher matcher = pattern.matcher(firstname);
        Matcher matcher2 = pattern2.matcher(firstname);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        }else if(matcher.find()){
            this.firstname = firstname;
        }else{
            throw new IllegalArgumentException("The firstname must contain \"[a-zA-Z]\" only ");
        }
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname only if it has at least 2 characters and does not contain illegal characters. illegal characters
     * are: 0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>         Throws an Exception if there are illegal arguments.
     * @param lastname string value for the last name of a contact
     */
    public void setLastname(String lastname) {
        Pattern pattern = Pattern.compile("[a-zA-ZäöüÄÖÜ]");
        Pattern pattern2 = Pattern.compile("[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        Matcher matcher = pattern.matcher(lastname);
        Matcher matcher2 = pattern2.matcher(lastname);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        }else if(matcher.find()){
            this.lastname = lastname;
        }else{
            throw new IllegalArgumentException("The lastname must contain \"[a-zA-Z]\" only");
        }
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

    /**
     * Sets the E-Mail of a contact only if there is a "@" character. Throws an Exception if there is no "@".
     * @param email
     */
    public void setEmail(String email) {
       if(email.contains("@")) {
           this.email = email;
       }else{
           throw new IllegalStateException("An E-Mail have to contain an @ symbol");
       }
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

    public LocalDate getDayOfBirth() {
        return dayOfBirth;
    }

    public boolean yearFormatCheck(String inputYear){
       boolean format = false;
       if(inputYear.length() == 9 && inputYear.matches("[0-9]") && inputYear.contains("-")){
           format = true;
       }
        return format;
    }

    /**
     * Sets the Date of birth only if it has this format: yy-MM-dd. the method takes three int values. The method will
     * check if the values of month and day have only one int value. if so, there will be automatically a "0" added to
     * ensure the format rule. The method checks also if 0 < day < 31, 0 < month < 12 and year > 0. If the input does not
     * require the formatting rules, an ISE will be thrown
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDayOfBirth(int year, int month, int day) {
         dayOfBirth = LocalDate.of(year, month, day);
    }

    public void setDayOfBirth(LocalDate dayOfBirth) {
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

    /**
     * Sets the institute if it is not null.
     * @param institutes
     */
    public void setInstitutes(Set<Institute> institutes) {
        if(institutes != null) {
            this.institutes = institutes;
        } else{
            throw new IllegalStateException("institute should be initialized");
        }
    }
    public String getSearchString(){
        searchString = firstname + " " + lastname + " " + email + " " + lastname + ", " + firstname;
        return searchString;
    }
}
