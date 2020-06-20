package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactFinder {


    public ContactFinder(){
    }
    public Set<Contact> findContacts(String searchWord, List<Contact> allContacts) {
        Set<Contact> foundContacts = new HashSet<>();
        for (Contact elem : allContacts) {
            if (elem.getFirstname().contains(searchWord)) {
                foundContacts.add(elem);
                System.out.println(elem.getFirstname());
            }
        }
        return foundContacts;
    }
}
