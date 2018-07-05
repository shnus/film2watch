package com.shnus.film2watch.model;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Nusrat on 2/12/2017.
 */
@Value
@Builder
public class User implements Serializable {
    long id;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String gender;
    String bio;
    String location;
    String loginName;
    String passwordHash;
    String image_b64;


    public String getName() {
        return firstName + " " + lastName;
    }
}
