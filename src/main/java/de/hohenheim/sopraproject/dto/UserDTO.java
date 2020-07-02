package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.User;


public class UserDTO {

    private User user;
    private boolean admin = false;

    public UserDTO(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
