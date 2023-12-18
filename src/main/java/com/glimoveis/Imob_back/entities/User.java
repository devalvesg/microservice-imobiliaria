package com.glimoveis.Imob_back.entities;

import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import com.glimoveis.Imob_back.DTOs.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String name;

    @NotBlank(message = "O campo email não pode ser em branco")
    private String email;

    @NotBlank(message = "O campo senha não pode ser em branco")
    private String password;

    @NotNull(message = "O campo cpf não pode ser em branco")
    private String cpf;

    @NotBlank(message = "O campo telefone não pode ser em branco")
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Immobiles> immobiles;

    public User(String name, String email, String cpf, String phone){
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
    }

    public User(UserDTO userDTO){
        this.name = userDTO.name();
        this.email = userDTO.email();
        this.cpf = userDTO.cpf();
        this.phone = userDTO.phone();
        this.password = userDTO.password();
    }
}
