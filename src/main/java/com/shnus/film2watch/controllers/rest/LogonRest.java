package com.shnus.film2watch.controllers.rest;

import com.shnus.film2watch.model.AjaxResponseBody;
import com.shnus.film2watch.model.UserBean;
import com.shnus.film2watch.service.UserServiceImpl;
import com.shnus.film2watch.service.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class LogonRest {

    private final
    UserServiceImpl userService;

    @Autowired
    public LogonRest(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/api/logon")
    public ResponseEntity<?> getUser(@Valid @RequestBody UserBean user, Errors errors) {
        AjaxResponseBody result = new AjaxResponseBody();
        if (errors.hasErrors()) {
            result.setStatus(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        Validation validation = userService.checkUser(user);
        if (!validation.getValid().isEmpty()) {

            if (validation.getValid().get("USER_EXIST") != null) {
                result.setCredentialsValidation("USER_EXIST");
            }

            if (validation.getValid().get("INVALID_EMAIL") != null) {
                result.setCredentialsValidation("INVALID_EMAIL");
            }

            if (validation.getValid().get("INVALID_NAME") != null) {
                result.setNameValidation("INVALID_NAME");
            }

            if (validation.getValid().get("SHORT_PASSWORD") != null) {
                result.setPasswordValidation("INVALID_PASSWORD");
            }

            result.setStatus("invalid");
        } else {
            if (userService.createUser(user)) {
                result.setStatus("success");
            } else {
                result.setStatus("db problems");
            }
        }
        return ResponseEntity.ok(result);
    }
}
