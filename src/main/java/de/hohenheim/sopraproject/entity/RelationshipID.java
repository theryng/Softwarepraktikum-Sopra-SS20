package de.hohenheim.sopraproject.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * This method implements two attributes of type Contact which have a many to one mapping to the Relationship class to
 * create a table which is able to save the data of both contacts of plus a short description of the type of the
 * relationship between the contacts and the date since when the relationship lasts. With this class and the double one
 * to many relationship, a many to many relationship is avoided to add more columns to the PK /FK Table.
 * */
@Embeddable
public class RelationshipID implements Serializable {

    private Contact contactA;

    private Contact contactB;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Contact getContactA() {
        return contactA;
    }

    public void setContactA(Contact contactA) {
        this.contactA = contactA;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Contact getContactB() {
        return contactB;
    }

    public void setContactB(Contact contactB) {
        this.contactB = contactB;
    }
}
