package com.glimoveis.Imob_back.services;

import com.glimoveis.Imob_back.DTOs.AuthenticationDTO;
import com.glimoveis.Imob_back.DTOs.Responses.LoginResponseDTO;
import com.glimoveis.Imob_back.DTOs.Responses.UserResponse;
import com.glimoveis.Imob_back.configs.SecurityConfigs.TokenService;
import com.glimoveis.Imob_back.models.User;
import com.glimoveis.Imob_back.producers.EmailProducer;
import com.glimoveis.Imob_back.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private EmailProducer emailProducer;

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private TokenService tokenService;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService, EmailProducer emailProducer, BCryptPasswordEncoder passwordEncoder) {
        this.emailProducer = emailProducer;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User data) throws Exception {
        if (userRepository.findByEmailUser(data.getEmail()) != null)
            throw new Exception("Usuario já cadastrado no sistema");
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getName(), data.getEmail(), encryptedPassword, data.getCpf(), data.getPhone());

        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser.getId(), data.getName(), data.getEmail());
        this.emailProducer.publishMessage(userResponse);
    }

    public LoginResponseDTO login(AuthenticationDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());

        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        LoginResponseDTO loginResponse = new LoginResponseDTO(token);
        return loginResponse;
    }

    public void changePassword(User user, String currentPassword, String newPassword) throws Exception {
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new Exception("A senha informada está incorreta!");
        }
        String encodeNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodeNewPassword);
        userRepository.save(user);
    }

}
