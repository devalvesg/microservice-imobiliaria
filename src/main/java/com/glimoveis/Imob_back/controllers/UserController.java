package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.AuthenticationDTO;
import com.glimoveis.Imob_back.DTOs.LoginResponseDTO;
import com.glimoveis.Imob_back.config.TokenService;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity newUser(@RequestBody @Valid User data){
        if(userRepository.findByEmailUser(data.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        User newUser = new User(data.getName(), data.getEmail(), encryptedPassword, data.getCpf(), data.getPhone());

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try{
            var userNamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());

        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        LoginResponseDTO loginResponse = new LoginResponseDTO(token);
        return ResponseEntity.ok(loginResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("As credenciais não coincidem com nosso banco de dados");
        }
    }
}
