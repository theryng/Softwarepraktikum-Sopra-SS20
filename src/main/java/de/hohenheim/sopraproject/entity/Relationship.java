package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents the Relationship relation in the database. It shows which relationships two cantacts can have
 * with each other. It has two many to one relationships with contact: Contact A and Contact B. Relationship ID is the primary key.
 */
@Entity
public class Relationship {
    @Id
    @GeneratedValue
    private Integer relationshipID;

    private String typeOfRelationship;

    private String since;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contact contactA;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contact contactB;

    public Relationship(Integer relationshipID, String typeOfRelationship, int year, int month, int day) {
        this.relationshipID = relationshipID;
        this.typeOfRelationship = typeOfRelationship;
        setSince(year, month, day);
    }

    public Relationship(){}

    public Integer getRelationshipID() {
        return relationshipID;
    }

    public void setRelationshipID(Integer relationshipID) {
        this.relationshipID = relationshipID;
    }

    public String getTypeOfRelationship() {
        return typeOfRelationship;
    }

    public void setTypeOfRelationship(String typeOfRelationship) {
        this.typeOfRelationship = typeOfRelationship;
    }

    public String getSince() {
        return since;
    }

    /**
     * Sets the date since when the relationship between two contacts consists. It takes  in they int year, int month
     * and int day and the output is the following date-format: YYYY-MM-DD. If the month or the day value only have one
     * digit, it adds a zero before this digit. The method also checks if 0 < day < 31, 0 < month < 12 and year > 0. If
     * the input does not fit the formatting rules, an EXception will be thrown.
     */
    public void setSince(int year, int month, int day) {

        String stringOfYear = Integer.toString(year);
        String stringOfMonth = Integer.toString(month);
        String stringOfDay = Integer.toString(day);

        if(stringOfMonth.length() == 1){
            stringOfMonth = "0" + stringOfMonth;
        }

        if(stringOfDay.length() == 1){
            stringOfDay = "0" + stringOfDay;
        }

        if(day > 31 || day < 1 || month > 12 || month < 1 || year < 0){
            throw new IllegalStateException("Illegal state of year, month or day");
        }
        if(stringOfYear.length() == 4  &&
                stringOfMonth.length() == 2 &&
                stringOfDay.length() == 2) {

            this.since = stringOfYear + "-" + stringOfMonth + "-" + stringOfDay;

        } else {
            throw new IllegalStateException("Date has to be in this format: yyyy-MM-dd");
        }
    }

    public Contact getContactA() {
        return contactA;
    }

    public void setContactA(Contact contactA) {
        this.contactA = contactA;
    }

    public Contact getContactB() {
        return contactB;
    }

    public void setContactB(Contact contactB) {
        this.contactB = contactB;
    }
}
