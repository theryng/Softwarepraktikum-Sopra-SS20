package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.repository.RoleRepository;
import de.hohenheim.sopraproject.repository.UserRepository;
import de.hohenheim.sopraproject.service.RoleService;
import de.hohenheim.sopraproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

/**
 * This class controls all important methods during use of the userDetails page
 *
 * Inside this controller there are all important methods that helps updating the data of all users of this application.
 * It helps creating a new password, overriding data of a specific user as well as simple navigation aspects for the different
 * authority checks of a user.
 *
 * @date 14.07.2020
 * @author Chris Hasselbach
 */
@Controller
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    //encodes the given password in order to make it usable
    public UserDetailsController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method gives out the user and all the necessary attributes
     *
     * This method gives out all the necessary attributes of the chosen user i order to create the detailspage.
     * The user will be determined by its username! Since it function like a ID it has to be a unique username created during the
     * registration process
     *
     *
     * @param username
     * @param model
     * @return userdetails
     */
    @GetMapping("/userDetails/{username}")
    public String userDetails(@PathVariable("username") String username, Model model) {

        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);

        return "userDetails";
    }

    /**
     * Overrides password of a user
     *
     * This method overrides the password of the user and binds the new one to it. No other attributes are changed
     * besides the password. The password encoder hashes the password and makes it readable to the application.
     *
     * @param user
     * @param result
     * @return userDetails || registration
     */
    @PostMapping("/overridePassword")
    public String userPassword(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "userDetails";
        }

        //encodes new Password and binds it to account
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        //ensures all userrights remain the same after overriding data
        if (user.getIsAdmin()) {
            Role admin = new Role("ROLE_ADMIN");
            Set<Role> adminRoles = new HashSet<>();

            roleService.saveRole(admin);
            adminRoles.add(admin);
            user.setRoles(adminRoles);
        } else {
            Role normalUser = new Role("ROLE_USER");
            Set<Role> userRoles = new HashSet<>();

            roleService.saveRole(normalUser);
            userRoles.add(normalUser);
            user.setRoles(userRoles);
        }

        //Check if username already exists. If so userDetails wont be overwritten
        if (userRepository.findByUsername(user.getUsername()) != null && userRepository.findByUserId(user.getUserId()) == null) {
            result.hasErrors();
            return "redirect:/registration";
        }

        //saves new Data of the user
        if (!userService.getUserById(user.getUserId()).equals(user.getUserId())) {
            userService.saveUser(user);
        }

        //checks if user was an admin. If not user will be redirected to the updated userDetails page. The admin returns to the registration page
        if (user.getIsAdmin() == false) {
            return "redirect:/userDetails/" + user.getUsername();
        } else {
            return "redirect:/registration";
        }
    }

    /**
     * Overrides the users account information
     *
     * This method updates the account information with new Data if there has been changes made to them. No other
     * attributes will be overwritten besides the changed ones.
     *
     * @param user
     * @param result
     * @return userDetails || registration
     */
    @PostMapping("/overrideUser")
    public String userDetails(@ModelAttribute("user") @Valid User user, BindingResult result) {

        String oldPassword = userService.getUserById(user.getUserId()).getPassword();

        if (result.hasErrors()) {
            return "userDetails";
        }

        user.setUsername(user.getUsername());

        //encodes new Password and binds it to account
        if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword() == oldPassword) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            //if the password hasnt changed save old one again
            user.setPassword(oldPassword);
        }

        //ensures all userrights remain the same after overriding data
        if (user.getIsAdmin()) {
            Role admin = new Role("ROLE_ADMIN");
            Set<Role> adminRoles = new HashSet<>();

            roleService.saveRole(admin);
            adminRoles.add(admin);
            user.setRoles(adminRoles);
        } else {
            Role normalUser = new Role("ROLE_USER");
            Set<Role> userRoles = new HashSet<>();

            roleService.saveRole(normalUser);
            userRoles.add(normalUser);
            user.setRoles(userRoles);
        }

        //New userData will be saved
        if (!userService.getUserById(user.getUserId()).equals(user.getUserId())) {
            userService.saveUser(user);
        }

        //checks if user was an admin. If not user will be redirected to the updated userDetails page. The admin returns to the registration page
        if (user.getIsAdmin() == false) {
            return "redirect:/userDetails/" + user.getUsername();
        }

        return "redirect:/registration";
    }


    /**
     * Closes the details page (ROLE_ADMIN)
     *
     * This method closes the userDetails page and returns to the registration page. Only used if the user has the role ROLE_ADMIN
     * @return registration
     */
    @PostMapping(value = "/backUserDetails")
    public String backUserDetails() {
        return "redirect:/registration";
    }


    /**
     * Closes the details page (ROLE_USER)
     *
     * This method closes the userDetails page and returns to the homepage. Only used if the user has the role ROLE_USER
     * @return home
     */
    @PostMapping(value = "/backHome")
    public String backHome(){
        return "redirect:/home";
    }

}
