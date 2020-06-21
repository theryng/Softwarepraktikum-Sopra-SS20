package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class defines all attributes that are necessary to define a Contacthistory. There is one Contacthistory for every existing Contact. The primary key is "contacthistoryId"
 * and it has a man to one relation to "Contact".
 */
@Entity
public class ContactHistory {

    @Id
    @GeneratedValue
    private Integer contactHistoryID;

    public String date;

    public String text;

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Contact> contactOfHistory;


    public ContactHistory() {
        //empty constructor for Hibernate
    }

    public Set<Contact> getContactOfHistory() {
        return contactOfHistory;
    }

    public void setContactOfHistory(Set<Contact> contactOfHistory) {
        this.contactOfHistory = contactOfHistory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
