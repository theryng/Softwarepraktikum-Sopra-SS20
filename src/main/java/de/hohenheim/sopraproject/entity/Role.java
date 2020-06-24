package de.hohenheim.sopraproject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The class Role of this application
 *
 * This class sets the role of each and every user inside this application. Every role has to set Ids that helps distinct
 * every role from eachother and can be viewed inside the database. Every role has its own name as well to further
 * distinct themselves.
 *
 * @date 26.06.2020
 * @author
 */
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;

    public Role() {
        //empty constructor for Hibernate
    }

    //Konstruktor of the class
    public Role(String rolename) {
        this.rolename = rolename;
    }

    /**
     * Getter of the attribute id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of the attribute id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the attribute rolename
     * @return rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * Setter of the attribute rolename
     * @param rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}