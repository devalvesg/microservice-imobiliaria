package com.glimoveis.Imob_back.DTOs;

import com.glimoveis.Imob_back.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserDTO(String name, String email, String password, String cpf, String phone) {

}
