package com.glimoveis.emailsending.repositories;

import com.glimoveis.emailsending.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {
}
