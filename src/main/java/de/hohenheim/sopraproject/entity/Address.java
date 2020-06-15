package de.hohenheim.sopraproject.entity;

import javax.persistence.*;

/**
 * This is a component class which is used to define the adress of a contact. because of that it has the @Embeddable
 * annotation. To define an adress, this class goes with the attributes: ZIP code, city, street and house number. The
 * attribute names will be the names of the column in the contact table.
 */
@Embeddable
public class Address {

    public String zipCode;

    public String city;

    public String street;

    public String houseNumber;

    public Address(){};

    public Address(String zipCode, String city, String street , String houseNumber) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
