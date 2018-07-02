package com.shnus.film2watch.service;


import com.shnus.film2watch.dao.UserDao;
import com.shnus.film2watch.dao.UserDaoImpl;
import com.shnus.film2watch.model.Credentials;
import com.shnus.film2watch.model.User;
import com.shnus.film2watch.model.UserBean;
import com.shnus.film2watch.service.interfaces.SecurityService;
import com.shnus.film2watch.service.interfaces.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final SecurityService securityService;

    @Autowired
    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.userDao = new UserDaoImpl(jdbcTemplate);
        this.securityService = new SecurityServiceImpl();
    }

    @Override
    public Validation checkUser(UserBean user) {
        final Validation validation  = Validation.builder().build();

        if(userDao.isExist(user.login)) {
            validation.getValid().put("USER_EXIST", true);
        } else if(!EmailValidator.getInstance().isValid(user.login)){
            validation.getValid().put("INVALID_EMAIL", true);
        }

        if(!(( (user.firstName+user.lastName).matches("[a-zA-Z]+") ) &&
                (user.firstName.length()>1) )) {
            validation.getValid().put("INVALID_NAME", true);
        }

        if(user.password.length()<4){
            validation.getValid().put("SHORT_PASSWORD", true);
        }

        return validation;
    }

    @Override
    public Optional<User> getByCredentials(Credentials credentials) {
        final Optional<User> userOptional = userDao.getByLoginName(credentials.getLogin());
        if(!userOptional.isPresent()){
            return Optional.empty();
        }
        final User user = userOptional.get();
        if(!securityService.validate(credentials.getPassword(), user.getPasswordHash())){
            return Optional.empty();
        }

        return Optional.of(user);
    }


    public Optional<User> getByUsername(String username) {
        final Optional<User> userOptional = userDao.getByLoginName(username);
        if(!userOptional.isPresent()){
            return Optional.empty();
        }

        return userOptional;
    }

    @Override
    public boolean createUser(UserBean user) {

        if(user.birthDate.contains("!")){
            user.birthDate="0001-01-01";
        }
        if(user.image_b64.length() < 10){
            user.image_b64 = "https://cdn2.iconfinder.com/data/icons/website-icons/512/User_Avatar-256.png";
        }

        return userDao.createUser(
                User.builder()
                        .loginName(user.login)
                        .passwordHash(securityService.encrypt(user.password))
                        .firstName(user.firstName)
                        .lastName(user.lastName)
                        .gender(user.gender)
                        .birthDate(LocalDate.parse(user.birthDate))
                        .location(user.location)
                        .bio("")
                        .image_b64(user.image_b64)
                        .build());
    }

}
