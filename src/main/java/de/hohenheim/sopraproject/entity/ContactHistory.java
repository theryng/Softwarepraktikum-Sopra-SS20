package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines all attributes that are necessary to define a Contacthistory. There is one Contacthistory for every
 * existing Contact. The primary key is "contacthistoryId" and it has a many to one relation to "Contact".
 */
@Entity
public class ContactHistory {

    @Id
    @GeneratedValue
    private Integer contactHistoryID;

    public String date;

    public String text;

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Contact> contactOfHistory = new HashSet<>();


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
        contactOfHistory.add(contact);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the date of the Contacthistory. The Exception ensures the right Date-format
     * @param year
     * @param month
     * @param day
     */
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

}
