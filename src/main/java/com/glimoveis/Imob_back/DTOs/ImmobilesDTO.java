package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.entities.Adress;
import com.glimoveis.Imob_back.entities.Informations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public record ImmobilesDTO(String title, String description, String type, Adress adress, Informations informations, List<String> images) {
}
