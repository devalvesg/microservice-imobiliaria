package com.glimoveis.Imob_back.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "informations")
public class Informations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "immobile_id") @JsonIgnore
    private Immobiles immobiles;

    @NotBlank(message = "O valor do imóvel deve ser válido.")
    private BigDecimal value;

    @NotBlank(message = "O número de quartos deve ser válido!")
    private Integer rooms;

    @NotBlank(message = "O número de banheiros deve ser válido!")
    private Integer bathrooms;

    @NotBlank(message = "O número de vagas na garagem deve ser válido!")
    private Integer parkingLots;

    @NotBlank(message = "A metragem do terreno deve ser válido!")
    private Double landFootage;

    @NotBlank(message = "A metragem de cronstrução deve ser válido!")
    private Double constructionFootage;


}
