package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ContactFinder {


    public ContactFinder(){
    }
    public LinkedList<Contact> findContacts(String searchWord, List<Contact> allContacts) {
        LinkedList<Contact> foundContacts = new LinkedList<>();
        for (Contact elem : allContacts) {
            if (elem.getSearchString().contains(searchWord)) {
                foundContacts.add(elem);
            }
        }
        return foundContacts;
    }
}
