package com.shnus.film2watch.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LogonController {
    @GetMapping("/logon")
    public ModelAndView logonPage() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("logon", model);
    }
}
