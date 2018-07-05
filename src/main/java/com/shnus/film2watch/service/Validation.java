package com.shnus.film2watch.service;

import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Builder
@Value
public class Validation {
    Map<String, Boolean> valid = new HashMap();

    public Validation() {

    }
}
