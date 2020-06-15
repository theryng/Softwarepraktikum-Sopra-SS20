package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contact contactA;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Contact contactB;

    public Relationship(Integer relationshipID, String typeOfRelationship, String since) {
        this.relationshipID = relationshipID;
        this.typeOfRelationship = typeOfRelationship;
        this.since = since;
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

    public void setSince(String since) {
        this.since = since;
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
