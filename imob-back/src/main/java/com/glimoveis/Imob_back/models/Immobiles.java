package com.glimoveis.Imob_back.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glimoveis.Imob_back.DTOs.ImmobilesDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_immobiles")
public class Immobiles {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id") @JsonIgnore
    private User user;

    @NotBlank(message = "O campo de titulo não pode ser vázio") @Size(max = 30, message = "O campo de titulo não pode ser maior que 30 caracteres")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "informations_id")
    private Informations informations;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "O campo de descrição não pode ser vázio") @Size(min = 20, max = 255, message = "O campo de descrição não pode ser menor que 20 e maior que 255 caracteres")
    private String description;

    private String type;

    @JsonFormat(pattern = "dd/MM/yyyy | HH:mm")
    private LocalDateTime datePublish;

    private List<String> images;
    public Immobiles(String title, Informations informations, Address address, String description, String type, List<String> images){
        this.title = title;
        this.address = address;
        this.informations = informations;
        this.description = description;
        this.type = type;
        this.images = images;
    }

    public Immobiles(ImmobilesDTO immobilesDTO){
        this.title = immobilesDTO.title();
        this.address = immobilesDTO.address();
        this.informations = immobilesDTO.informations();
        this.description = immobilesDTO.description();
        this.type = immobilesDTO.type();
        this.images = immobilesDTO.images();
    }
}
