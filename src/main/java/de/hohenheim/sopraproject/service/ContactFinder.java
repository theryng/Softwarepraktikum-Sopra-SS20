package de.hohenheim.sopraproject.service;

import de.hohenheim.sopraproject.entity.*;

import java.util.LinkedList;
import java.util.List;

public class ContactFinder {


    public ContactFinder(){
    }

    public LinkedList<Contact> findContacts(String searchWord, List<Contact> allContacts) {
        LinkedList<Contact> foundContacts = new LinkedList<>();
        for (Contact elem : allContacts) {
            if (elem.getSearchString().toLowerCase().contains(searchWord.toLowerCase())) {
                foundContacts.add(elem);
            }
        }
        return foundContacts;
    }

    public LinkedList<Event> findEvents(String searchWord, List<Event> allEvents) {
        LinkedList<Event> foundEvents = new LinkedList<>();
        for (Event elem : allEvents) {
            if (elem.getSearchString().toLowerCase().contains(searchWord.toLowerCase())) {
                foundEvents.add(elem);
            }
        }
        return foundEvents;
    }

    public LinkedList<Project> findProjects(String searchWord, List<Project> allProjects) {
        LinkedList<Project> foundProjects = new LinkedList<>();
        for (Project elem : allProjects) {
            if (elem.getSearchString().toLowerCase().contains(searchWord.toLowerCase())) {
                foundProjects.add(elem);
            }
        }
        return foundProjects;
    }

    public LinkedList<Tags> findTags(String searchWord, List<Tags> allTags) {
        LinkedList<Tags> foundTags = new LinkedList<>();
        for (Tags elem : allTags) {
            if (elem.getSearchString().toLowerCase().contains(searchWord.toLowerCase())) {
                foundTags.add(elem);
            }
        }
        return foundTags;
    }

    public LinkedList<Institute> findInstitutes(String searchWord, List<Institute> allInstitutes) {
        LinkedList<Institute> foundInstitutes = new LinkedList<>();
        for (Institute elem : allInstitutes) {
            if (elem.getSearchString().toLowerCase().contains(searchWord.toLowerCase())) {
                foundInstitutes.add(elem);
            }
        }
        return foundInstitutes;
    }
}
