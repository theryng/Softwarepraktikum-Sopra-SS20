package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.dto.UserDTO;
import de.hohenheim.sopraproject.entity.User;
import de.hohenheim.sopraproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserDetailsController {

    @Autowired
    private UserService userService;

    private PasswordEncoder passwordEncoder;


    @GetMapping("/userDetails/{username}")
    public String userDetails(@PathVariable("username") String username, Model model) {
        System.out.println("Testing the stuff2 " + username);
        User user = userService.getUserByUsername(username);

        model.addAttribute("user", user);
        return "userDetails";
    }

    @PostMapping("/overrideUser")
    public String userDetails(@Valid UserDTO userDto, BindingResult result,Model model) {
        System.out.println(userDto.getUsername());
        if(result.hasErrors()){
            model.addAttribute("userDto",userDto);
            return "userDetails";
        }
        else{
            User user = userDto.getUser();
            userDto.setUsername(userDto.getUsername());
            if(!userService.getUserByUsername(user.getUsername()).equals(user)){
                userService.saveUser(user);
            }
            userDto.setUser(user);
            model.addAttribute("userDto",userDto);
            return "redirect:/userDetails/"+userDto.getUsername();
        }
    }


    @PostMapping(value = "/backUserDetails")
    public String backUserDetails() {
        return "redirect:/registration";
    }

}
