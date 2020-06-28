package de.hohenheim.sopraproject.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class user of this application
 *
 * The class user stores all important attributes a user of this application has. A user can also be an admin or just a
 * regular user. This property decides what sites a certain user can view/visit.
 *
 * @date 26.06.2020
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isAdmin = false;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    //Construktor for this class
    public User(String username, String password, Set<Role> roles, String firstName, String lastName, boolean enabled){
        this.username = username;
        this.roles = roles;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public User() {
        // empty constructor for Hibernate
    }

    /**
     * Getter for the attribute events
     * @return events
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Setter for the attribute events
     * @param events
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * Getter for the attribute userId
     * @return userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Setter for the attribute userId
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Getter for the attribute username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username only if it does not contain illegal characters. Illegal characters
     * are: 0-9?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>      Throws an Exception if there are illegal arguments.
     * @param username
     */
    public void setUsername(String username) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Pattern pattern2 = Pattern.compile("[?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        Matcher matcher = pattern.matcher(username);
        Matcher matcher2 = pattern2.matcher(username);

        if(matcher2.find()) {
            throw new IllegalArgumentException("No characters of this kind are allowed: " +
                    "[?!¡¿“¶[]|{}≠€§$%&/()=`+#'.,{´]^°<>]");
        }else if(matcher.find()  && username.length()>1){
            this.username = username;
        }else{
            throw new IllegalArgumentException("The username must contain \"[a-zA-Z0-9]\" only and has to be greater than " +
                    "one digit long");
        }
    }

    /**
     * Getter for the attribute password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the attribute password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the boolean attribute enabled
     * @return  boolean enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Setter for the boolean attribute enabled
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter for the attribute roles
     * @return roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setter for the attribute roles
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Getter for the attribute firstName
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of the attribute firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of the attribute lastName
     * @return lastName
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Setter for the attribute lastName
     * @param lastName
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Getter for the boolean attribute isAdmin
     * @return isAdmin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Setter for the boolean attribute isAdmin
     * @param isAdmin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
