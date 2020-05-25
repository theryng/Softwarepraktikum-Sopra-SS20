package de.hohenheim.sopraproject.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Institute {

    @Id
    @GeneratedValue
    private Integer instituteID;

    private String name;

    private String location;

    @OneToMany
    private Set<Contact> contacts;

    public Institute(){
        //empty constructor for Hibernate
    }

    public Institute(Integer instituteID, String name, String location) {
        this.instituteID = instituteID;
        this.name = name;
        this.location = location;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer institutID) {
        this.instituteID = institutID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String ort) {
        this.location = ort;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }
}
