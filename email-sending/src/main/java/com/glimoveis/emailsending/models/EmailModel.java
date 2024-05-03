package com.glimoveis.emailsending.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_email")
public class EmailModel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailId;

    @NotBlank
    private String userId;

    @NotBlank
    @Email
    private String emailRemetente;

    @NotBlank
    @Email
    private String emailDestinatario;

    @NotBlank
    private String assunto;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    private LocalDateTime dataEnvio;

    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;

}
