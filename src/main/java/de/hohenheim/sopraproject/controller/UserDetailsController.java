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


    public UserDetailsController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/userDetails/{username}")
    public String userDetails(@PathVariable("username") String username, Model model) {
        System.out.println("Testing the stuff2 " + username);
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);

        return "userDetails";
    }

    //User hat zwar admin properties left aber kann nicht registration page erreichen!
    @PostMapping("/overridePassword")
    public String userPassword(@ModelAttribute("user") @Valid User user,BindingResult result) {
        System.out.println("Das Passwort des Users lautet: " + user.getPassword());


        if (result.hasErrors()) {
            return "userDetails";
        }

        //encodes new Password and binds it to account
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        //ensures all userrights remain the same after overriding data
        if(user.getIsAdmin()){
            System.out.println("Der User müsste ein Admin sein");
            Role admin = new Role("ROLE_ADMIN");
            Set<Role> adminRoles = new HashSet<>();

            roleService.saveRole(admin);
            adminRoles.add(admin);
            user.setRoles(adminRoles);
        }else{
            Role normalUser = new Role("ROLE_USER");
            Set<Role> userRoles = new HashSet<>();

            roleService.saveRole(normalUser);
            userRoles.add(normalUser);
            user.setRoles(userRoles);
        }


        //Check if username already exists. If so userDetails wont be overwritten
        if (userRepository.findByUsername(user.getUsername()) != null && userRepository.findByUserId(user.getUserId()) == null) {
            result.hasErrors();
            System.out.println("Nichts gespeichert");
            return "redirect:/registration";
        }

        if (!userService.getUserById(user.getUserId()).equals(user.getUserId())) {
            userService.saveUser(user);
        }

        if (user.getIsAdmin() == false) {
            return "redirect:/userDetails/" + user.getUsername();
        } else {
            return "redirect:/registration";
        }
    }

    @PostMapping("/overrideUser")
    public String userDetails(@ModelAttribute("user") @Valid User user, BindingResult result) {
        System.out.println(user.getPassword());
        System.out.println(user.getRoles());

        String oldPassword = userService.getUserById(user.getUserId()).getPassword();

        if(result.hasErrors()){
            return "userDetails";
        }

        user.setUsername(user.getUsername());

        //encodes new Password and binds it to account
        if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword()==oldPassword) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }else{
            System.out.println("altes Password");
            user.setPassword(oldPassword);
        }

        //ensures all userrights remain the same after overriding data
        if(user.getIsAdmin()){
            System.out.println("Der User müsste ein Admin sein");
            Role admin = new Role("ROLE_ADMIN");
            Set<Role> adminRoles = new HashSet<>();

            roleService.saveRole(admin);
            adminRoles.add(admin);
            user.setRoles(adminRoles);
        }else{
            Role normalUser = new Role("ROLE_USER");
            Set<Role> userRoles = new HashSet<>();

            roleService.saveRole(normalUser);
            userRoles.add(normalUser);
            user.setRoles(userRoles);
        }

        //Check if username already exists. If so userDetails wont be overwritten
        if(userRepository.findByUsername(user.getUsername()) != null && userRepository.findByUserId(user.getUserId()) == null){
                result.hasErrors();
                System.out.println("Nichts gespeichert");
                return "redirect:/registration";
            }

        //New userData will be saved
        if(!userService.getUserById(user.getUserId()).equals(user.getUserId())){
                userService.saveUser(user);
        }

        if(user.getIsAdmin() == false){
            return "redirect:/userDetails/"+user.getUsername();
        }

        return "redirect:/registration";
        }



    @PostMapping(value = "/backUserDetails")
    public String backUserDetails() {
        return "redirect:/registration";
    }


    @PostMapping(value = "/backHome")
    public String backHome(){
        return "redirect:/home";
    }

}
