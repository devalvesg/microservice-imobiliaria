package com.glimoveis.emailsending.controllers;

import com.glimoveis.emailsending.dtos.UserResponse;
import com.glimoveis.emailsending.models.EmailModel;
import com.glimoveis.emailsending.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("emails/boas-vindas")
    public ResponseEntity boasVindas(@RequestBody @Valid UserResponse userResponse){
        EmailModel emailModel = new EmailModel();
        emailModel.setDataEnvio(LocalDateTime.now());
        emailModel.setUserId(userResponse.id());
        emailModel.setEmailDestinatario(userResponse.email());

        emailService.envioBoasVindas(emailModel, userResponse.name());

        return ResponseEntity.status(HttpStatus.CREATED).body("Email de boas vindas disparado");
    }
}
