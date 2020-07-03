package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Institute;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InstituteDTO {
    private Institute institute = new Institute();
    private List<Institute> allInstitutes = new LinkedList<>();
    private List<Contact> foundInstitutes = new LinkedList<>();
    private List<Contact> chosenInstitutes = new LinkedList<>();
    private String searchWord = "";
    public Integer instituteID;
    public Contact contact = new Contact();
    public Integer contactTempID;

    public InstituteDTO() {
    }

    public List<Institute> getAllInstitutes() {
        return allInstitutes;
    }

    public void setAllInstitutes(List<Institute> allInstitutes) {
        this.allInstitutes = allInstitutes;
    }

    public Integer getContactTempID() {
        return contactTempID;
    }
    public void setContactTempID(Integer contactTempID) {
        this.contactTempID = contactTempID;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }


    public List<Contact> getFoundInstitutes() {
        return foundInstitutes;
    }

    public void setFoundInstitutes(List<Contact> foundInstitutes) {
        this.foundInstitutes = foundInstitutes;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Contact> getChosenInstitutes() {
        return chosenInstitutes;
    }

    public void setChosenInstitutes(List<Contact> chosenInstitutes) {
        this.chosenInstitutes = chosenInstitutes;
    }
}
