package com.shnus.film2watch.controllers;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {


    @GetMapping("/login")
    public ModelAndView mainPage(){
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("login", model);
    }

    @RequestMapping(value = "api/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String Submit(@RequestParam("name") String name, @RequestParam("location") String location) {
        System.out.println("jdksjhkjlk");
        return "pizda";
    }

}