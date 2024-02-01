package com.glimoveis.Imob_back.DTOs;

public class LoginResponseDTO {

    private String token;
    public LoginResponseDTO(){

    }

    public LoginResponseDTO(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}


