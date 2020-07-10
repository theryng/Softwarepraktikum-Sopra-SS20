package de.hohenheim.sopraproject.service;


import de.hohenheim.sopraproject.entity.Tags;

import java.util.LinkedList;
import java.util.List;

public class EventFinder {


    public EventFinder(){
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
