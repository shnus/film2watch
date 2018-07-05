package com.shnus.film2watch.dao;

import com.shnus.film2watch.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getByLoginName(String loginName) {
        String query = "SELECT * FROM users " +
                "WHERE username=?";
        Object[] args = new String[1];
        args[0] = loginName;
        List users = jdbcTemplate.query(query, args, (ResultSetExtractor<List>) rs -> {
            List<User> usersList = new ArrayList<>();
            while (rs.next()) {
                User user = User.builder()
                        .id(rs.getInt("id"))
                        .loginName(rs.getString("username"))
                        .passwordHash(rs.getString("password"))
                        .firstName(rs.getString("firstname"))
                        .lastName(rs.getString("lastname"))
                        .gender(rs.getString("gender"))
                        .birthDate(LocalDate.parse(rs.getString("birthday")))
                        .location(rs.getString("location"))
                        .bio(rs.getString("bio"))
                        .image_b64(rs.getString("image"))
                        .build();
                usersList.add(user);
            }
            return usersList;
        });

        if (users != null) {
            if (users.size() > 0) {
                return Optional.ofNullable((User) users.get(0));
            }
        }
        return Optional.empty();
    }


    @Override
    public boolean createUser(User user) {
        String query = "INSERT INTO users(username,"
                + "password,firstname,lastname,gender,birthday,location,bio,image) VALUES "
                + "(?, ?, ?, ?, ?, str_to_date(?,'%Y-%m-%d'), ?, ?, ?);";

        int result = jdbcTemplate.update(
                query,
                user.getLoginName(), user.getPasswordHash(),
                user.getFirstName(), user.getLastName(),
                user.getGender(), user.getBirthDate().toString(),
                user.getLocation(), user.getBio(), user.getImage_b64());

        if (result > 0) {
            query = "INSERT INTO user_role (username, role) VALUES \n" +
                    "(?, 'ROLE USER')";
            result = jdbcTemplate.update(query, user.getLoginName());
        }
        return result > 0;
    }

    @Override
    public boolean isExist(String login) {
        Optional<User> user = getByLoginName(login);
        return user.isPresent();
    }


}

