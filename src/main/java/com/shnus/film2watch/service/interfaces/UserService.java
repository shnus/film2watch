package com.shnus.film2watch.service.interfaces;


import com.shnus.film2watch.model.Credentials;
import com.shnus.film2watch.model.User;
import com.shnus.film2watch.model.UserBean;
import com.shnus.film2watch.service.Validation;

import java.util.Optional;


public interface UserService {
    Validation checkUser(UserBean user);

    Optional<User> getByCredentials(Credentials credentials);

    boolean createUser(UserBean user);
}
