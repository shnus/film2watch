package com.shnus.film2watch.dao;

import com.shnus.film2watch.model.User;

import java.util.Optional;


public interface UserDao {
    Optional<User> getByLoginName(String loginName);
    boolean createUser(User user);

    boolean isExist(String login);


}
