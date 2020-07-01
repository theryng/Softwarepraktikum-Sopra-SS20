package de.hohenheim.sopraproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    private String since;
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
    public Project(Integer projectID, String name, String description, String since, Set<Contact> projectContacts,
                   Set<Institute> institutes, int year, int month, int day) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        setSince(year, month, day);
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

    public String getSince() {
        return since;
    }

    /**
     * Sets the Date of birth only if it has this format: yy-MM-dd. the method takes three int values. The method will
     * check if the values of month and day have only one int value. if so, there will be automatically a "0" added to
     * ensure the format rule. The method checks also if 0 < day < 31, 0 < month < 12 and year > 0. If the input does not
     * require the formatting rules, an ISE will be thrown
     *
     * @param year
     * @param month
     * @param day
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
