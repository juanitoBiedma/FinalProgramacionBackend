package com.minseg.spring.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Auth {

    @NotEmpty()
    private String username;

    @NotEmpty()
    private String password;
}