package com.glimoveis.emailsending.dtos;

import com.glimoveis.emailsending.models.StatusEmail;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record EmailDTO(String userId, String emailRemetente, String emailDestinatario, String assunto, String conteudo, LocalDateTime dataEnvio, StatusEmail statusEmail){
}
