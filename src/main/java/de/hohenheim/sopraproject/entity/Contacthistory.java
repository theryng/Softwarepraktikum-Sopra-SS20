package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contacthistory {

    @Id
    @GeneratedValue
    public Integer contacthistoryId;

    public String date;

    public String text;

    @ManyToOne
    public Contact contact;

    public Contacthistory() {
        //empty constructor for Hibernate
    }

    public Contacthistory(String date, String text, Contact contact) {
        this.date = date;
        this.text = text;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
