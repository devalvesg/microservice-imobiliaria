package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.entities.User;

public class AuthenticationDTO {
    private String email;
    private String password;

    public AuthenticationDTO(){
    }

    public AuthenticationDTO(User user){
        email = user.getEmail();
        password = user.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


