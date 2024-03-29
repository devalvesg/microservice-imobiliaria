package com.glimoveis.Imob_back.controllers;

import com.glimoveis.Imob_back.DTOs.*;
import com.glimoveis.Imob_back.config.SecurityConfigs.TokenService;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.producers.EmailProducer;
import com.glimoveis.Imob_back.repositories.UserRepository;
import com.glimoveis.Imob_back.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity newUser(@RequestBody @Valid User data){
        try{
            userService.register(data);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try{
           LoginResponseDTO loginResponseDTO = userService.login(data);
           return ResponseEntity.ok(loginResponseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("As credenciais não coincidem com nosso banco de dados");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity userData(@AuthenticationPrincipal User user){
        try{
            UserDTO response = UserDTO.builder()
                    .cpf(user.getCpf())
                    .email(user.getEmail())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .build();
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Você precisa estar logado para realizar essa ação!");
        }
    }

    @PostMapping("/profile/changePassword")
    public ResponseEntity changePassword(@AuthenticationPrincipal User user, @RequestBody @Valid PasswordDTO passwordDTO){
        try{
            userService.changePassword(user, passwordDTO.currentPassword(), passwordDTO.newPassword());
            return ResponseEntity.ok("Senha alterada com sucesso!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    public void loginGoogle() {

    }
}
