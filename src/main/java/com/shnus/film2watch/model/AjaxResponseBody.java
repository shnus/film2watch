package com.shnus.film2watch.model;

import lombok.Data;

@Data
public class AjaxResponseBody {

    public String status;
    public String credentialsValidation;
    public String nameValidation;
    public String passwordValidation;

}