package com.glimoveis.Imob_back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_adress")
public class Adress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo de estado não pode ficar vázio")
    private String state;
    @NotBlank(message = "O campo de cidade não pode ficar vázio")
    private String city;
    private String street;
    @NotBlank(message = "O campo de bairro não pode ficar vázio")
    private String neighborhood;
    private String number;

    @OneToOne(mappedBy = "adress") @JsonIgnore
    private Immobiles immobiles;

    public Adress(String street, String neighborhood, String number) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.number = number;
    }
}
