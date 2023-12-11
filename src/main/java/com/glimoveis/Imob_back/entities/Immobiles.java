package com.glimoveis.Imob_back.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "immobiles")
public class Immobiles {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id") @JsonIgnore
    private User user;

    @NotBlank(message = "O campo de titulo não pode ser vázio") @Size(max = 30, message = "O campo de titulo não pode ser maior que 30 caracteres")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informations_id")
    private Informations informations;

    @NotBlank(message = "O campo de descrição não pode ser vázio") @Size(min = 20, max = 255, message = "O campo de descrição não pode ser menor que 20 e maior que 255 caracteres")
    private String description;

    private String type;

    @JsonFormat(pattern = "dd/MM/yyyy | HH:mm")
    private LocalDateTime datePublish;

    public Immobiles(String title, Informations informations,Adress adress, String description, String type){
        this.title = title;
        this.adress = adress;
        this.informations = informations;
        this.description = description;
        this.type = type;
    }

    public Immobiles(ImmobilesDTO immobilesDTO){
        this.title = immobilesDTO.title();
        this.adress = immobilesDTO.adress();
        this.informations = immobilesDTO.informations();
        this.description = immobilesDTO.description();
        this.type = immobilesDTO.type();
    }
}
