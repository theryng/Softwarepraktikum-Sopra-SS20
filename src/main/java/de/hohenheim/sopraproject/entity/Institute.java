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
 * An Institute is in most of the cases the workplace on which an alumni is active. This class which is represented as a
 * table in the database has a primary key which is the instituteID and some other attributes. It also has an one to
 * many relationship with the customer. ALl the attribute names will later be the column names of the table.
 * Institue ID is the primary key.
 */
@Entity
public class Institute {

    @Id
    @GeneratedValue
    private Integer instituteID;

    private String name;

    private String linkToHomepage;

    @Embedded
    private Address address = new Address();
    @Transient
    private String tempZipCode;
    @Transient
    private String tempHouseNmbr;
    @Transient
    private String tempCity;
    @Transient
    private String tempStreet;

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

    public String getTempZipCode() {
        return tempZipCode;
    }

    public void setTempZipCode(String tempZipCode) {
        this.tempZipCode = tempZipCode;
    }

    public String getTempHouseNmbr() {
        return tempHouseNmbr;
    }

    public void setTempHouseNmbr(String tempHouseNmbr) {
        this.tempHouseNmbr = tempHouseNmbr;
    }

    public String getTempCity() {
        return tempCity;
    }

    public void setTempCity(String tempCity) {
        this.tempCity = tempCity;
    }

    public String getTempStreet() {
        return tempStreet;
    }

    public void setTempStreet(String tempStreet) {
        this.tempStreet = tempStreet;
    }

    public String getLinkToHomepage() {
        return linkToHomepage;
    }
    public void setLinkToHomepage(String linkToHomepage) {
        this.linkToHomepage = linkToHomepage;
    }

}
