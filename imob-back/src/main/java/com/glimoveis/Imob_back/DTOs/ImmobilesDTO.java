package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.models.Address;
import com.glimoveis.Imob_back.models.Informations;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record ImmobilesDTO(String title, String description, String type, Address address, Informations informations) {

}
