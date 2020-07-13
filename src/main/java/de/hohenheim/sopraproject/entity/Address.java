package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a component class which is used to define an address. because of that it has the @Embeddable
 * annotation. To define an address, this class goes with the attributes: ZIP code, city, street and house number. The
 * attribute names will be the names of the column in the contact table.
 */
@Embeddable
public class Address {

    public String zipCode;

    public String city;

    public String street;

    public String houseNumber;

    public Address(){};

    /**
     * The constructor which calls the setter methods of the specified attributes, to ensure that all the defensive
     * programming which were made in the special setter methods has its validity, even if the attributes are called in
     * the constructor. This ensures that only attributes are set to the constructor which requires the conditions.
     * @param zipCode
     * @param city
     * @param street
     * @param houseNumber
     */
    public Address(String zipCode, String city, String street , String houseNumber) {
        setZipCode(zipCode);
        setCity(city);
        setStreet(street);
        setHouseNumber(houseNumber);
    }


    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * This setter method to set a house number allows only a expressions which contains a number from 0 to 9.
     * This ensures that no nonsense hose number can be set.
     * @param houseNumber the input String value of a house number
     */
    public void setHouseNumber(String houseNumber) {
       Pattern pattern = Pattern.compile("[0-9a-zA-Z/-]");
       Matcher matcher = pattern.matcher(houseNumber);

       if(matcher.find()) {
           this.houseNumber = houseNumber;
       }else{
           throw new IllegalArgumentException("The house number must contain \"[0-9]\"");
       }
    }

    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets only a value for zipCode if the zipCode contains out of numbers only and is greater than three and smaller
     * than seven digits long. If the requirements do not fulfills, an IllegalArgumentException will be thrown
     * @param zipCode the input String for a zip code Value of an Address object
     */
    public void setZipCode(String zipCode) {
        if(zipCode.matches("[0-9]+") && zipCode.length()<7 && zipCode.length()>3) {
            this.zipCode = zipCode;
        }else{
            throw new IllegalArgumentException("The zipCode must contain only numbers and has to be greater than three " +
                    "and smaller than seven digits");
        }
    }

    public String getCity() {
        return city;
    }

    /**
     * sets the city name only if it contains letters and has at least three digits. Else it will throw an IAE.
     * Special character like "-", "/" and whitespaces are allowed to use.
     * @param city input String for the street name
     */
    public void setCity(String city) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Pattern pattern2 = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(city);
        Matcher matcher2 = pattern2.matcher(city);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No numbers allowed in the city name");
        }else if(matcher.find()  && city.length()>2){
            this.city = city;
        }else{
            throw new IllegalArgumentException("The city name must contain \"[a-zA-Z]\" only and has to be greater than " +
                    "two digits long");
        }
    }

    public String getStreet() {
        return street;
    }

    /**
     * sets the street name only if it contains letters and has at least three digits. Else it will throw an IAE.
     * Special character like "-", "/" and whitespaces are allowed to use.
     * @param street input String for the street name
     */
    public void setStreet(String street) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Pattern pattern2 = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(street);
        Matcher matcher2 = pattern2.matcher(street);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No numbers allowed in the street name");
        }else if(matcher.find() && street.length()>2){
                this.street = street;
        }else{
            throw new IllegalArgumentException("The street name must contain \"[a-zA-Z]\" only and has to be greater than " +
                    "two and digits long");
        }
    }
}
