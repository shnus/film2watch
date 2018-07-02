package com.shnus.film2watch.controllers.rest;

import com.shnus.film2watch.model.FilmBean;
import com.shnus.film2watch.model.User;
import com.shnus.film2watch.service.FilmServiceImpl;
import com.shnus.film2watch.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public @ResponseBody ResponseEntity<?> getWatchlist() {
        List<FilmBean> result;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.getByUsername(auth.getName());
        result = filmService.getWatchlist(userOptional.get().getId());
        return ResponseEntity.ok(result);
    }

}
