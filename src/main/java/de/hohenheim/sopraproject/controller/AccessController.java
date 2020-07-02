package de.hohenheim.sopraproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController {

    @RequestMapping("/accessdenied")
    public String accessdenied() {
        return "accessDenied";
    }
}
