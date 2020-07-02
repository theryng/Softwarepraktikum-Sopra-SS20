package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Relationship;

import java.util.LinkedList;
import java.util.List;

public class RelationshipDTO {
    Integer contactA;
    Integer contactB;
    Relationship relationship;
    List<Contact> foundContact = new LinkedList<Contact>();
    String searchWord = "";

    public RelationshipDTO() {
    }

    public Integer getContactA() {
        return contactA;
    }

    public void setContactA(Integer contactA) {
        this.contactA = contactA;
    }

    public Integer getContactB() {
        return contactB;
    }

    public void setContactB(Integer contactB) {
        this.contactB = contactB;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public List<Contact> getFoundContact() {
        return foundContact;
    }

    public void setFoundContact(List<Contact> foundContact) {
        this.foundContact = foundContact;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
