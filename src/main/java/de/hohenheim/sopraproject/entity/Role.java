package de.hohenheim.sopraproject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    private String rolename;




    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> user;


    public Role() {
        //empty constructor for Hibernate
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}