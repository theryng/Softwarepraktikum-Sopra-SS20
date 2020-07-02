package de.hohenheim.sopraproject.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The Project class represents an project entity in the database. It represents all projects on which alumni can work
 * on. An Example could be: Alumni Anna works on a corona tracking app. Projects can refer to institutes from which the
 * projects are commissioned. Example: Alumni Anna works on a corona tracking app which is commissioned by the Institute
 * SAP
 *
 * @date 01.07.2020
 *
 * @author markwagner
 */
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer projectID;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate since;
    @ManyToMany
    private Set<Contact> projectContacts = new HashSet<>();
    @ManyToMany
    private Set<Institute> projectInstitutes = new HashSet<>();

    /**
     * constructor filled with necessary setter to require error handling
     * @param projectID
     * @param name
     * @param description
     * @param since
     * @param projectContacts
     * @param institutes
     */
    public Project(Integer projectID, String name, String description, Set<Contact> projectContacts,
                   Set<Institute> institutes, LocalDate since) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.since = since;
        this.projectContacts = projectContacts;
        this.projectInstitutes = institutes;
    }

    public Project(){
        //Constructor for Hibernate
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSince() {
        return since;
    }

    public void setSince(int year, int month, int day) {
        this.since = LocalDate.of(year, month, day);
    }

    public void setSince(LocalDate date) {
        this.since = date;
    }

    public Set<Contact> getProjectContacts() {
        return projectContacts;
    }

    public void setProjectContacts(Set<Contact> projectContacts) {
        this.projectContacts = projectContacts;
    }

    public Set<Contact> getContacts() {
        return projectContacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.projectContacts = projectContacts;
    }

    public Set<Institute> getProjectInstitutes() {
        return projectInstitutes;
    }

    public void setProjectInstitutes(Set<Institute> institutes) {
        this.projectInstitutes = institutes;
    }

    public void addProjectContact(Contact contact) {
        if (contact != null) {
            projectContacts.add(contact);
        }
    }

    public void addProjectInstitutes(Institute institute) {
        if (institute != null) {
            projectInstitutes.add(institute);
        }
    }
}
