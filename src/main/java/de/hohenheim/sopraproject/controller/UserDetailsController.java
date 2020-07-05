package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.entity.Role;
import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.repository.RoleRepository;
import de.hohenheim.sopraproject.repository.UserRepository;
import de.hohenheim.sopraproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserDetailsController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserDetailsController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/userDetails/{userId}")
    public String userDetails(@PathVariable("userId") Integer userId, Model model) {
        System.out.println("Testing the stuff2 " + userId);
        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        return "userDetails";
    }

    @PostMapping("/overrideUser")
    public String userDetails(@ModelAttribute("user") @Valid User user, BindingResult result) {
        System.out.println(user.getPassword());



        if(result.hasErrors()){
            return "/userDetails";
        }


        user.setUsername(user.getUsername());


        //encodes new Password and binds it to account
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }



         //wenn username geändert wird -> Password gelöscht
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
        return "redirect:/registration";
        }


    @PostMapping(value = "/backUserDetails")
    public String backUserDetails() {
        return "redirect:/registration";
    }

}
