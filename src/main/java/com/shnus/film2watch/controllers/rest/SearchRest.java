package com.shnus.film2watch.controllers.rest;

import com.shnus.film2watch.model.AjaxResponseBody;
import com.shnus.film2watch.model.FilmBean;
import com.shnus.film2watch.model.User;
import com.shnus.film2watch.service.FilmServiceImpl;
import com.shnus.film2watch.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SearchRest {

    private final UserServiceImpl userService;
    private final FilmServiceImpl filmService;

    @Autowired
    public SearchRest(UserServiceImpl userService, FilmServiceImpl filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @PostMapping("/api/addToWatchlist")
    public ResponseEntity<?> getUser(@Valid @RequestBody FilmBean filmBean, Errors errors) {
        AjaxResponseBody result = new AjaxResponseBody();
        if (errors.hasErrors()) {
            result.setStatus(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.getByUsername(auth.getName());
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body(result);
        }
        if (filmService.filmExist(userOptional.get().getId(), filmBean.id)) {
            result.setStatus("exist");
        } else {
            result.setStatus("success");
            filmService.addFilm(filmBean, userOptional.get().getId());
        }
        return ResponseEntity.ok(result);
    }
}
