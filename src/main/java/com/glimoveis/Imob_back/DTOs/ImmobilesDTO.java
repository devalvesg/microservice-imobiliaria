package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.entities.Adress;
import com.glimoveis.Imob_back.entities.Informations;

import java.util.Date;

public record ImmobilesDTO(String title, String description, String type, Adress adress, Informations informations) {
}
