package com.glimoveis.Imob_back.repositories;

import com.glimoveis.Imob_back.models.Immobiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImmobileRepository extends JpaRepository<Immobiles, Long> {

	List<Immobiles> findByType(String type);
}
