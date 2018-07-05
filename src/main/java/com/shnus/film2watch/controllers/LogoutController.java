package com.shnus.film2watch.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public ModelAndView mainPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("logout");
        }
        return new ModelAndView("logout", new HashMap<>());
    }

}
