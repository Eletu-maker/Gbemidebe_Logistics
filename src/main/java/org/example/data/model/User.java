package org.example.data.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.lang.annotation.Documented;

@Data

public abstract class User {
    private String name;
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    //private boolean isValidPassword;
    private String phoneNumber;
    private boolean login = false;


    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
}
