package com.github.nazzrrg.simplespringangularapp.payload.request;

import lombok.Data;

import java.util.Set;
@Data
public class SignupRequest {
    private String name;
    private String email;
    //private Set<String> role;
    private String password;
}