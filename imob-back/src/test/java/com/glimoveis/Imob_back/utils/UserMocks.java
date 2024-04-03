package com.glimoveis.Imob_back.utils;

import com.glimoveis.Imob_back.DTOs.UserDTO;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.User;

public class UserMocks {

    public static String ID = "cb26e693-dc5f-4a33-bdbc-4201261f0a4e";
    public static String NAME = "nome teste";
    public static String EMAIL = "email teste";
    public static String PASSWORD = "123456";
    public static String CPF = "654833655-96";
    public static String PHONE = "(16) 99249-4674";



    public static UserDTO mockUserDTO(){
        return UserDTO.builder()
                .cpf(CPF)
                .email(EMAIL)
                .name(NAME)
                .phone(PHONE)
                .password(PASSWORD)
                .build();
    }

    public static User mockUserEntity(){
        User user = new User(mockUserDTO());
        user.setId(ID);
        return user;
    }
}
