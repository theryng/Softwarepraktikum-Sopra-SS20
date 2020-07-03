package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.Contact;
import de.hohenheim.sopraproject.entity.Project;

import java.util.LinkedList;
import java.util.List;

public class ProjectDTO {

    private Project project = new Project();
    private List<Project> allProjects = new LinkedList<>();
    private List<Contact> foundProjects = new LinkedList<>();
    private List<Contact> chosenProjects = new LinkedList<>();
    private String searchWord = "";
    public Integer projectID;
    public Contact contact = new Contact();
    public Integer contactTempID;

    public ProjectDTO() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getAllProjects() {
        return allProjects;
    }

    public void setAllProjects(List<Project> allProjects) {
        this.allProjects = allProjects;
    }

    public List<Contact> getFoundProjects() {
        return foundProjects;
    }

    public void setFoundProjects(List<Contact> foundProjects) {
        this.foundProjects = foundProjects;
    }

    public List<Contact> getChosenProjects() {
        return chosenProjects;
    }

    public void setChosenProjects(List<Contact> chosenProjects) {
        this.chosenProjects = chosenProjects;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Integer getContactTempID() {
        return contactTempID;
    }

    public void setContactTempID(Integer contactTempID) {
        this.contactTempID = contactTempID;
    }
}
