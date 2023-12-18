package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.UserDTO;
import com.glimoveis.Imob_back.entities.User;
import com.glimoveis.Imob_back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/")
public class UserController {

    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/novo-usuario")
    public ResponseEntity newUser(@RequestBody @Valid UserDTO userDTO){
        try{
            User user = new User(userDTO);
            userService.newUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Houve um erro ao criar o usuario, verifique os dados e tente novamente!");
        }
    }
}
