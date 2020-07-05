package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.UserDTO;
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
import java.util.Optional;

@Controller
public class UserDetailsController {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userDetails/{username}")
    public String userDetails(@PathVariable("username") Integer userId, Model model) {
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

        //Overwrite old password
       // if (!StringUtils.isEmpty(user.getPassword())) {
         //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        //}

        //Check if username already exists. If so userDetails wont be overwritten
        if(userRepository.findByUsername(user.getUsername()) != null){
            result.hasErrors();
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
