package de.hohenheim.sopraproject.entity;

import javax.persistence.*;

/**
 * This class represents the Relationship relation in the database. it shows which relationships two cantacts can have
 * with each other.
 */
@Entity
public class Relationship {
    @Id
    @GeneratedValue
    private Integer relationshipID;

    private String typeOfRelationship;

    private String since;

    private String ingoingString;

    private int partnerRelationshipID;

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

    public String getIngoingString() {
        return ingoingString;
    }

    public void setIngoingString(String ingoingString) {
        this.ingoingString = ingoingString;
    }

    public int getPartnerRelationship() {
        return partnerRelationshipID;
    }

    public void setPartnerRelationship(int partnerRelationshipID) {
        this.partnerRelationshipID = partnerRelationshipID;
    }
}
