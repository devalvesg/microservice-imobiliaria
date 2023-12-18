package com.glimoveis.Imob_back.DTOs;

import java.math.BigDecimal;

public record UserDTO(String name, String email, String password, String cpf, String phone) {
}
