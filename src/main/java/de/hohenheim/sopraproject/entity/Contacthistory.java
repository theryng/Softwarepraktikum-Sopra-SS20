package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class defines all attributes that are necessary to define a Contacthistory. There is one Contacthistory for every existing Contact. The primary key is "contacthistoryId"
 * and it has a man to one relation to "Contact".
 */
@Entity
public class Contacthistory {

    @Id
    @GeneratedValue
    private Integer contacthistoryId;

    private String date;

    private String text;

    @OneToOne
    private Contact ownerOfHistory;

    @OneToMany
    private Set<Contact> contact = new HashSet<>();

    public Contacthistory() {
        //empty constructor for Hibernate
    }

    public Contact getOwnerOfHistory() {
        return ownerOfHistory;
    }

    public void setOwnerOfHistory(Contact ownerOfHistory) {
        this.ownerOfHistory = ownerOfHistory;
    }

    public Set<Contact> getContact() {
        return contact;
    }

    public void setContact(Set<Contact> contact) {
        this.contact = contact;
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

    public void setDate(String date) {
        this.date = date;
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
