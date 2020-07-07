package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Tags;
import de.hohenheim.sopraproject.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ContactFinder {


    public ContactFinder(){
    }
    public LinkedList<Contact> findContacts(String searchWord, List<Contact> allContacts, String searchOption) {
        if(searchOption.equals("Tag")){
            LinkedList<Contact> foundContacts = new LinkedList<>();
            for (Contact elem : allContacts) {
                for(Tags tag : elem.getTags()){
                    if (tag.getName().contains(searchWord)) {
                        foundContacts.add(elem);
                    }
                }
            }
            return foundContacts;
        }
        else if(searchOption.equals("Name")){
            LinkedList<Contact> foundContacts = new LinkedList<>();
            for (Contact elem : allContacts) {
                String name = elem.getFirstname()+elem.getLastname();
                if (name.contains(searchWord)) {
                    foundContacts.add(elem);
                }
            }
            return foundContacts;
        }
        else if(searchOption.equals("Studium")){
            LinkedList<Contact> foundContacts = new LinkedList<>();
            for (Contact elem : allContacts) {
                if (elem.getCourseOfStudies().contains(searchWord)) {
                    foundContacts.add(elem);
                }
            }
            return foundContacts;
        }

        return new LinkedList<>();
    }
    public LinkedList<Tags> findTags(String searchWord, List<Tags> allTags) {
        LinkedList<Tags> foundTags = new LinkedList<>();
        for (Tags elem : allTags) {
            if (elem.getSearchString().contains(searchWord)) {
                foundTags.add(elem);
            }
        }
        return foundTags;
    }
}
