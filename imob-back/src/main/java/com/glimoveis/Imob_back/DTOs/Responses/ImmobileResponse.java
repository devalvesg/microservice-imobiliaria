package com.glimoveis.Imob_back.DTOs.Responses;

import com.glimoveis.Imob_back.models.Address;
import com.glimoveis.Imob_back.models.Immobiles;
import com.glimoveis.Imob_back.models.Informations;
import com.glimoveis.Imob_back.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImmobileResponse {
    private Long id;
    private String title;
    private String description;
    private String type;
    private Address address;
    private Informations informations;
    private LocalDateTime datePublish;
    private String userId;
    private List<String> images;


    public ImmobileResponse(Immobiles immobiles){
        this.id = immobiles.getId();
        this.title = immobiles.getTitle();
        this.description = immobiles.getDescription();
        this.type = immobiles.getType();
        this.address = immobiles.getAddress();
        this.informations = immobiles.getInformations();
        this.datePublish = immobiles.getDatePublish();
        this.images = immobiles.getImages();
    }

}
