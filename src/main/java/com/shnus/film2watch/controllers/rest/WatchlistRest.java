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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class WatchlistRest {
    private final
    UserServiceImpl userService;
    FilmServiceImpl filmService;

    @Autowired
    public WatchlistRest(UserServiceImpl userService, FilmServiceImpl filmService) {
        this.userService = userService;
        this.filmService = filmService;
    }

    @GetMapping("/api/getWatchlist")
    public @ResponseBody
    ResponseEntity<?> getWatchlist() {
        List<FilmBean> result;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.getByUsername(auth.getName());
        result = filmService.getWatchlist(userOptional.get().getId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/rateFilm")
    public ResponseEntity<?> rateFilm(@Valid @RequestBody FilmBean filmBean, Errors errors) {
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
        if (filmService.rateFilm(userOptional.get().getId(), filmBean)) {
            result.setStatus("success");
            return ResponseEntity.ok(result);
        } else {
            result.setStatus("failed");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/api/deleteFromWatchlist")
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
            if (filmService.deleteFilm(filmBean, userOptional.get().getId())) {
                result.setStatus("success");
            } else {
                result.setStatus("failed");
                return ResponseEntity.badRequest().body(result);
            }
        } else {
            result.setStatus("deleted");
        }
        return ResponseEntity.ok(result);
    }

}
