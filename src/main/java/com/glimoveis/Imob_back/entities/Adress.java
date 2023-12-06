package com.glimoveis.Imob_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "Adress")
public class Adress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    @NotBlank(message = "O campo de bairro não pode ficar vázio")
    private String neighborhood;
    private String number;

    @OneToOne(mappedBy = "adress", cascade = CascadeType.ALL)
    private Immobiles immobiles;
}
