package com.shnus.film2watch.service.interfaces;

/**
 * Created by Nusrat on 2/25/2017.
 */

public interface SecurityService {
    String encrypt(String password);
    boolean validate(String password, String hash);
}
