package de.hohenheim.sopraproject.controller;

import de.hohenheim.sopraproject.repository.UserRepository;
import de.hohenheim.sopraproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value ="/registration", method = RequestMethod.GET)
    public String user(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allUsers", userRepository.findAll());
        System.out.println("On to Users");
        return "registration";
    }

    @RequestMapping(value="/registerUser", method = RequestMethod.POST)
    public String registerUser(User user){
        userRepository.save(user);
        System.out.println("register user");
        return "home";
    }

    @RequestMapping("/allUsers")
    public String allUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "registration";
    }

    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public String deleteUser(User user){
        userRepository.deleteById(user.getUserId());
        System.out.println("delete user");
        return "home";
    }
}
