package de.hohenheim.sopraproject.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Integer contactID;

    private String firstname;

    private String lastname;

   // private Integer age;

    private String occupation;

    private String email;

    private String courseOfStudies;

    private String freeText;

    private Date dayOfBirth;

    public Contact(Integer contactID, String firstname, String lastname/*, Integer age*/, String occupation, String email,
                   String courseOfStudies, String freeText, Date dayOfBirth) {
        this.contactID = contactID;
        this.firstname = firstname;
        this.lastname = lastname;
        //this.age = age;
        this.occupation = occupation;
        this.email = email;
        this.courseOfStudies = courseOfStudies;
        this.freeText = freeText;
        this.dayOfBirth = dayOfBirth;
    }

    public Contact() {
        //empty constructor for Hibernate
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer kontaktID) {
        this.contactID = kontaktID;
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
        setDayOfBirth(dayOfBirth);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String vorname) {
        this.firstname = vorname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String nachname) {
        this.lastname = nachname;
    }
/**
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer alter) {
        this.age = alter;
    }
 */

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String tätigkeit) {
        this.occupation = tätigkeit;
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

    public void setCourseOfStudies(String studiengang) {
        this.courseOfStudies = studiengang;
    }

    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freitext) {
        this.freeText = freitext;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }
}
