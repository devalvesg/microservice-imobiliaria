package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.models.Adress;
import com.glimoveis.Imob_back.models.Informations;

import java.util.List;

public record ImmobilesDTO(String title, String description, String type, Adress adress, Informations informations, List<String> images) {
}
