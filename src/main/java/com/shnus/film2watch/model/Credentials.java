package com.shnus.film2watch.model;

import lombok.Builder;
import lombok.Value;

/**
 * Created by Nusrat on 2/12/2017.
 */

@Builder
@Value
public class Credentials {
    String login;
    String password;
}
