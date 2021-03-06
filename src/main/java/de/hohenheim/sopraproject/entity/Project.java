package de.hohenheim.sopraproject.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An Institute is in most of the cases the workplace on which an alumni is active. This class which is represented as a
 * table in the database has a primary key which is the instituteID and some other attributes. It also has an one to
 * many relationship with the customer. ALl the attribute names will later be the column names of the table.
 * Institue ID is the primary key.
 */
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer projectID;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;

    private String description;

    @OneToMany
    private Set<ContactHistory> contactHistories = new HashSet<>();

    @ManyToMany
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany
    private Set<Institute> institutes = new HashSet<>();

    @ManyToMany (mappedBy = "projects", cascade = CascadeType.ALL)
    private Set<Tags> tags = new HashSet<>();

    public Project(){
        //empty constructor for Hibernate
    }

    public Project(Integer projectID, String name, LocalDate since, Set<Contact> contacts, Set<Institute> institutes) {
        this.projectID = projectID;
        this.name = name;
        this.since = since;
        this.contacts = contacts;
        this.institutes = institutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Pattern pattern = Pattern.compile("[a-zA-ZäöüÄÖÜ0-9-/]");
        Pattern pattern2 = Pattern.compile("[?!¡¿“¶[]|{}≠€§$%&()=`+#'.{´]^°<>]");
        Matcher matcher = pattern.matcher(name);
        Matcher matcher2 = pattern2.matcher(name);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[?!¡¿“¶[]|{}≠€§$%&()=`+#'.{´]^°<>]");
        }else if(matcher.find()){
            this.name = name;
        }else{
            throw new IllegalArgumentException("The name must contain \"[a-zA-ZäöüÄÖÜ0-9]\" only ");
        }
    }
    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setSince(int year, int month, int day) {
        this.since = LocalDate.of(year, month, day);
    }

    public void setSince(LocalDate date) {
        this.since = date;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public LocalDate getSince() {
        return since;
    }

    public Set<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(Set<Institute> institutes) {
        this.institutes = institutes;
    }

    /**
     * Adds a Contact to an Institution, only if the Contact exists.
     * @param contact
     */
    public void addProjectContacts(Contact contact){
        if(contact != null){
            contacts.add(contact);
        }
    }

    /**
     * Adds a Contact to an Institution, only if the Contact exists.
     * @param institute
     */
    public void addProjectInstitutes(Institute institute) {
        if (institute != null) {
            institutes.add(institute);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearchString(){
        return name+description;
    }

    public Set<ContactHistory> getContactHistories() {
        return contactHistories;
    }

    public void setContactHistories(Set<ContactHistory> contactHistories) {
        this.contactHistories = contactHistories;
    }
}

