package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContactDTO {

    private List<Contact> contacts = new LinkedList<Contact>();

    public ContactDTO() {
    }
    public ContactDTO(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
