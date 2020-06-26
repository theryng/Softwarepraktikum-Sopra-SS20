package de.hohenheim.sopraproject.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Transient
    private String searchString;

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
    public Set<Relationship> outgoingRelationships = new HashSet<>();

    @OneToMany(mappedBy = "contactB", cascade = CascadeType.ALL)
    public Set<Relationship> ingoingRelationships = new HashSet<>();

    @ManyToMany(mappedBy = "contacts", cascade =  CascadeType.ALL)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(mappedBy = "contacts", cascade = CascadeType.REMOVE)
    private Set<Institute> institutes = new HashSet<Institute>();

    @ManyToMany (mappedBy = "contactOfHistory", cascade = CascadeType.ALL)
    private Set<ContactHistory> contactHistory = new HashSet<>();


    public Contact(String firstname, String lastname, String occupation, String email,
                   String courseOfStudies, String freeText, int yearOfBirth, int monthOfBirth, int dayOfBirth) {

        setFirstname(firstname);
        setLastname(lastname);
        this.occupation = occupation;
        setEmail(email);
        this.courseOfStudies = courseOfStudies;
        this.freeText = freeText;
        setDayOfBirth(yearOfBirth, monthOfBirth, dayOfBirth);
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

    public Set<ContactHistory> getContactHistory() {
        return contactHistory;
    }

    /**
     * set the contact history of an contact only it is initialized
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

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public void setOutgoingRelationship(Set<Relationship> outgoingRelationship) {
        this.outgoingRelationships = outgoingRelationship;
    }

    public void setIngoingRelationship(Set<Relationship> ingoingRelationship) {
        this.ingoingRelationships = ingoingRelationship;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname only if it has at least 2 characters an does not contain illegal characters. illegal characters
     * are: 0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>
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
        }else if(matcher.find()  && firstname.length()>1){
            this.firstname = firstname;
        }else{
            throw new IllegalArgumentException("The firstname must contain \"[a-zA-Z]\" only and has to be greater than " +
                    "one digit long");
        }
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname only if it has at least 2 characters an does not contain illegal characters. illegal characters
     * are: 0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>
     * @param lastname string value for the first name of a contact
     */
    public void setLastname(String lastname) {
        Pattern pattern = Pattern.compile("[a-zA-ZäöüÄÖÜ]");
        Pattern pattern2 = Pattern.compile("[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        Matcher matcher = pattern.matcher(lastname);
        Matcher matcher2 = pattern2.matcher(lastname);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        }else if(matcher.find()  && lastname.length()>1){
            this.lastname = lastname;
        }else{
            throw new IllegalArgumentException("The lastname must contain \"[a-zA-Z]\" only and has to be greater than " +
                    "one digit long");
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
     * Sets the E-Mail only if there is an "@" character in it otherwise it will throw an IllegalStateException
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

    public String getDayOfBirth() {
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

                this.dayOfBirth = stringOfYear + "-" + stringOfMonth + "-" + stringOfDay;

        } else {
        throw new IllegalStateException("Date has to be in this format: yyyy-MM-dd");
        }
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
     * Sets the institute if it is not null, otherwise it will throw an IllegalStateException
     * @param institutes
     */
    public void setInstitutes(Set<Institute> institutes) {
        if(institutes != null) {
            this.institutes = institutes;
        } else{
            throw new IllegalStateException("institute should be initialized");
        }
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

    public String getSearchString(){
        searchString = firstname + lastname + linkToHomepage;
        return searchString;
    }
}
