package com.glimoveis.Imob_back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_informations")
public class Informations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "informations") @JsonIgnore
    private Immobiles immobiles;

    @NotNull(message = "O valor do imóvel deve ser válido.")
    private BigDecimal propertyValue;

    @NotNull(message = "O número de quartos deve ser válido!")
    private Integer rooms;

    @NotNull(message = "O número de banheiros deve ser válido!")
    private Integer bathrooms;

    @NotNull(message = "O número de vagas na garagem deve ser válido!")
    private Integer parkingLots;

    @NotNull(message = "A metragem do terreno deve ser válido!")
    private Double landFootage;

    @NotNull(message = "A metragem de cronstrução deve ser válido!")
    private Double constructionFootage;


    public Informations( BigDecimal propertyValue, Integer rooms, Integer bathrooms, Integer parkingLots, Double landFootage, Double constructionFootage) {
        this.propertyValue = propertyValue;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.parkingLots = parkingLots;
        this.landFootage = landFootage;
        this.constructionFootage = constructionFootage;
    }
}
