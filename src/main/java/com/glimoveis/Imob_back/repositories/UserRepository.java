package com.glimoveis.Imob_back.repositories;

import com.glimoveis.Imob_back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(nativeQuery = true, value = """
		SELECT * FROM users
		WHERE email = :email AND password = :password
			""")
    User login(String email, String password);

	UserDetails findByEmail(String email);


	@Query(nativeQuery = true, value = """
		SELECT * FROM users
		WHERE email = :email
			""")
	User findByEmailUser(String email);
}
