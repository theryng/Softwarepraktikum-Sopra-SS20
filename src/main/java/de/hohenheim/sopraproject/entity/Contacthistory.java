package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines all attributes that are necessary to define a Contacthistory. There is one Contacthistory for
 * every existing Contact. The primary key is "contacthistoryId" and it has a many to many relation to "Contact".
 * Contacthistories contain a free text, a date and the Contact related to the Contacthistory. Contactshistory ID is the primary key.
 */
@Entity
public class Contacthistory {

    @Id
    @GeneratedValue
    private Integer contacthistoryId;

    private String date;

    private String text;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Contact> contactOfHistory = new HashSet<>();

    public Contacthistory(Integer contacthistoryId, String date, String text, Set<Contact> contactOfHistory, int year,
                          int month, int day) {
        this.contacthistoryId = contacthistoryId;
        setDate(year, month, day);
        this.text = text;
        this.contactOfHistory = contactOfHistory;
    }

    public Contacthistory() {
        //empty constructor for Hibernate
    }

    /**
     * This method adds an existing Contact to a contacthistory, if the added contact exists.
     * @param contact
     */
    public void addContacthistoryContact(Contact contact){
        if(contact != null){
            contactOfHistory.add(contact);
        }
    }

    public Set<Contact> getContactOfHistory() {
        return contactOfHistory;
    }

    public void setContactOfHistory(Set<Contact> contactOfHistory) {
        this.contactOfHistory = contactOfHistory;
    }

    public Integer getKontakthistoryId() {
        return contacthistoryId;
    }

    public void setKontakthistoryId(Integer kontakthistoryId) {
        this.contacthistoryId = kontakthistoryId;
    }

    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the interaction with the contact. It takes  in three values: int year, int month and int day and
     * the output is the following date-format: YYYY-MM-DD. If the month or the day value only have one digit, it adds a
     * zero before this digit. The method also checks if 0 < day < 31, 0 < month < 12 and year > 0. If the input does not
     * fit the formatting rules, an Exception will be thrown.
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        this.date = date;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getContacthistoryId() {
        return contacthistoryId;
    }

    public void setContacthistoryId(Integer contacthistoryId) {
        this.contacthistoryId = contacthistoryId;
    }


}
