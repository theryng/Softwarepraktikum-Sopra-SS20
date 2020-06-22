package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.repository.UserRepository;
import de.hohenheim.sopraproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * This method gives out all the created users and their attributes.
     *
     * @param model
     * @return registration
     */
    @RequestMapping(value ="/registration", method = RequestMethod.GET)
    public String user(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allUsers", userRepository.findAll());
        System.out.println("On to Users");
        return "registration";
    }

    /**
     * This method creates a new user and saves it inside the database. Once a new user is created the program will
     * return to the homepage
     *
     * @param user
     * @return home
     */

    @RequestMapping(value="/registerUser", method = RequestMethod.POST)
    public String registerUser(User user){
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
        System.out.println("register user");
        return "home";
    }

    /**
     * This method gives out all the users in the database
     *
     * @param model
     * @return
     */
    @RequestMapping("/allUsers")
    public String allUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "registration";
    }

    @RequestMapping(value ="/setAdmin", method = RequestMethod.POST)
    public String setAdmin(User user, Role role){
        Role adminRole = new Role("ADMIN");
        user.setRoles((Set<Role>) adminRole);
        return "registration";
    }

    /**
     * This method deletes one user from the database. The deletion will be based on the user id so there wont be any
     * wrong accounts chosen
     *
     * @param user
     * @return home
     */
    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public String deleteUser(User user){
        userRepository.deleteById(user.getUserId());
        System.out.println("delete user");
        return "home";
    }

}

