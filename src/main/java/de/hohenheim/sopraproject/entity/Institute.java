package de.hohenheim.sopraproject.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private String email;

    @Embedded
    private Address address = new Address();

    @ManyToMany
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany
    private Set<Project> projects = new HashSet<>();

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
        Pattern pattern = Pattern.compile("[a-zA-ZäöüÄÖÜ]");
        Pattern pattern2 = Pattern.compile("[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.{´]^°<>]");
        Matcher matcher = pattern.matcher(name);
        Matcher matcher2 = pattern2.matcher(name);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.{´]^°<>]");
        }else if(matcher.find()){
            this.name = name;
        }else{
            throw new IllegalArgumentException("The firstname must contain \"[a-zA-Z]\" only ");
        }
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

    public String getLinkToHomepage() {
        return linkToHomepage;
    }
    public void setLinkToHomepage(String linkToHomepage) {
        this.linkToHomepage = linkToHomepage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.contains("@")) {
            this.email = email;
        }else{
            throw new IllegalStateException("An E-Mail have to contain an @ symbol");
        }
    }
}
