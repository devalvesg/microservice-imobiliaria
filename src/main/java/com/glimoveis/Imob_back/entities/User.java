package com.glimoveis.Imob_back.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome não pode ser em branco")
    private String name;
    @NotBlank(message = "O campo email não pode ser em branco")
    private String email;

    @NotNull(message = "O campo email não pode ser em branco")
    private BigDecimal cpf;

    @OneToMany(mappedBy = "user")
    private List<Immobiles> immobiles;

    public User(String name, String email, BigDecimal cpf){
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

}
