package com.glimoveis.Imob_back.repositories;

import com.glimoveis.Imob_back.entities.Immobiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmobileRepository extends JpaRepository<Immobiles, Long> {
}
