package de.hohenheim.sopraproject.dto;

import de.hohenheim.sopraproject.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDTO {

    private User user;
    private boolean admin = false;
    private String username;

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

    public String getUsername(){
        return username;
    }

}
