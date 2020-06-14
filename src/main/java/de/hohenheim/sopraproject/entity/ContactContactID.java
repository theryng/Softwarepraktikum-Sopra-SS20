package de.hohenheim.sopraproject.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ContactContactID implements Serializable {

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
