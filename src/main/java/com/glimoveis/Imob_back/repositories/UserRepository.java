package com.glimoveis.Imob_back.repositories;

import com.glimoveis.Imob_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
