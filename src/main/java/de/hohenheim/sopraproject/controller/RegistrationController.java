package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.UserDTO;
import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.repository.RoleRepository;
import de.hohenheim.sopraproject.repository.UserRepository;
import de.hohenheim.sopraproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.Binding;
import javax.naming.NameAlreadyBoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * This class controls all important methods to create a new user
 *
 * Insides this controller there are all the methods needed for a user of this appliction to create a new seperate account.
 * It helps to create a user, delete a user, show all information about the different users stored inside the database
 * and set another admin to help manage all different accounts
 *
 * @date 26.06.2020
 * @author Chris Hasselbach
 */
@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * This method gives out all the created users and their attributes.
     *
     * This method gives out all the created users and all of their attributes inside the database.
     *
     * @param model
     * @return registration
     */
    @RequestMapping(value ="/registration", method = RequestMethod.GET)
    public String user(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("allUsers", userRepository.findAll());
        return "registration";
    }

    /**
     * This method creates a new user and saves it inside the database. Once a new user is created the table will show the
     * data of the new user inside its table on registration.html
     *
     * First a user will be created and a password will be set. Once the password is confirmed again by the admin and a username
     * was chosen the submitting progress starts. First the password will be encrypted and binded to the user. Next the
     * role of said user will be decided. The admin role property will only be added to this user if the admin checkbox was checked
     * before submitting. Or else the standard user property will be bound to this account/user. Once the account was created the
     * side will be reloaded to update the table.
     *
     * @Boolean admin
     * @return registration
     */

    @Secured("ROLE_ADMIN")
    @PostMapping(value="/registerUser")
    public String registerUser(@ModelAttribute("user") @Valid UserDTO userDto, BindingResult result){

        boolean admin = userDto.getUser().getIsAdmin();

        if (!StringUtils.isEmpty(userDto.getUser().getPassword())) {
            userDto.getUser().setPassword(passwordEncoder.encode(userDto.getUser().getPassword()));
        }

        List<Role> roles = roleRepository.findAll();
        if(admin){
            for(Role rol : roles){
                if(rol.getRolename() == "ROLE_ADMIN"){
                    userDto.getUser().getRoles().add(rol);
                }
            }
        }
        else{
            for(Role rol : roles){
                if(rol.getRolename() == "ROLE_USER"){
                    userDto.getUser().getRoles().add(rol);
                }
            }
        }

        if(userRepository.findByUsername(userDto.getUser().getUsername()) != null){
            System.out.println("Gleicher Benutzername");
            return "redirect:/registration";
        }


        if(result.hasErrors()){
            return "redirect:/registration";
        }


        userRepository.save(userDto.getUser());
        return "redirect:/registration";
    }

    /**
     * This method gives out all the users in the database
     *
     * This method gives out the name of all users inside the existing database
     *
     * @param model
     * @return registration
     */
    @RequestMapping("/allUsers")
    public String allUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "registration";
    }

    /**
     * This method sets a users property to admin
     *
     * This method sets a newly created user accounts properties to admin upon creation
     *
     * @return registration
     */

    @RequestMapping(value ="/setAdmin", method = RequestMethod.POST)
    public String setAdmin(UserDTO userDto){
        boolean admin = userDto.getAdmin();
        return "registration";
    }

    /**
     * This method deletes one user from the database. The deletion will be based on the user id so there wont be any
     * wrong accounts chosen. Once the account was deleted the page will be reloaded.
     *
     * @return registration
     */
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public String deleteUser(User user){
        userRepository.deleteById(user.getUserId());
        return "redirect:/registration";
    }

}

