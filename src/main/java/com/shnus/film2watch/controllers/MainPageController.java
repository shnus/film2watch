package com.shnus.film2watch.controllers;

import com.shnus.film2watch.model.User;
import com.shnus.film2watch.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainPageController {

    final
    UserServiceImpl userService;

    @Autowired
    public MainPageController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/myPage")
    public ModelAndView mainPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.getByUsername(auth.getName());
        if(!userOptional.isPresent()){
            //TODO
        }
        User user = userOptional.get();
        Map<String, String> model = new HashMap<>();
        model.put("firstname",user.getFirstName());
        model.put("lastname",user.getLastName());
        if(user.getBio().length() < 1){
            model.put("bio","No info");
        } else {
            model.put("bio", user.getBio());
        }
        if(user.getLocation().length()<1){
            model.put("location","No info");
        } else {
            model.put("location", user.getLocation());
        }
        model.put("gender",user.getGender());
        model.put("image",user.getImage_b64());
        if(user.getBirthDate().toString().compareTo("1888-01-01")<0){
            model.put("birthdate","No info");
        } else {
            model.put("birthdate", user.getBirthDate().toString());
        }
        return new ModelAndView("myPage", model);
    }


}