package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.AuthenticationDTO;
import com.glimoveis.Imob_back.DTOs.LoginResponseDTO;
import com.glimoveis.Imob_back.DTOs.UserResponse;
import com.glimoveis.Imob_back.config.SecurityConfigs.TokenService;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.producers.EmailProducer;
import com.glimoveis.Imob_back.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/")
public class UserController {

    private EmailProducer emailProducer;

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private TokenService tokenService;

    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, EmailProducer emailProducer){
        this.emailProducer = emailProducer;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }



    @PostMapping("/register")
    public ResponseEntity newUser(@RequestBody @Valid User data){
        if(userRepository.findByEmailUser(data.getEmail()) != null) return ResponseEntity.badRequest().body("Email já cadastrado no sistema");
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getName(), data.getEmail(), encryptedPassword, data.getCpf(), data.getPhone());

        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser.getId(), data.getName(), data.getEmail());
        this.emailProducer.publishMessage(userResponse);

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


    public void loginGoogle() {

    }
}
