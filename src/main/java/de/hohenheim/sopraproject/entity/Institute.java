package de.hohenheim.sopraproject.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An Institute is in most of the cases the workplace on which a alumni is active. This class which is represented as a
 * table in the database has a primary key which is the instituteID and some other attributes. It also has an one to
 * many relationship with the customer. ALl the attribute names will later be the column names of the table.
 */
@Entity
public class Institute {

    @Id
    @GeneratedValue
    private Integer instituteID;

    private String name;

    @Embedded
    private Address address;

    @ManyToMany
    private Set<Contact> contacts = new HashSet<>();

    public Institute(){
        //empty constructor for Hibernate
    }

    public Institute(Integer instituteID, String name, String location) {
        this.instituteID = instituteID;
        this.name = name;
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

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Adds a Contact to an Institution, only if the Contact exists.
     * @param contact
     */
    public void addInstitutionContacts(Contact contact){
        if(contact != null){
            contacts.add(contact);
        }
    }
}
