package com.glimoveis.Imob_back.repositories;

import com.glimoveis.Imob_back.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
