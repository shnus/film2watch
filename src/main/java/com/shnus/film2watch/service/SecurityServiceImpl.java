package com.shnus.film2watch.service;

import com.shnus.film2watch.service.interfaces.SecurityService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class SecurityServiceImpl implements SecurityService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean validate(String password, String hash) {
        System.out.println(password);
        System.out.println(passwordEncoder.encode(password));
        System.out.println(hash);
        return hash.compareTo(passwordEncoder.encode(password)) == 0;
    }
}
