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
 * This class covers the User data. An User has a name and a password. It has a relation to the entity Role to identify
 * the role of the given user. One User can only have one role. User ID is the primary key.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer userId;

    private String username;

    private String password;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;


    public User() {
        // empty constructor for Hibernate
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
