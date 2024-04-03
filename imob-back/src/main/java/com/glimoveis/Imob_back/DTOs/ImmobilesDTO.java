package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.models.Address;
import com.glimoveis.Imob_back.models.Informations;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ImmobilesDTO(String title, String description, String type, Address address, Informations informations, List<String> images) {

}
