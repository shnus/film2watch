package com.shnus.film2watch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Nusrat on 3/25/2017.
 */
@Data
@AllArgsConstructor
public class UserBean {

    public String login;
    public String password;
    public String firstName;
    public String lastName;
    public String gender;
    public String birthDate;
    public String location;
    public String image_b64;

    public UserBean() {
    }

}
