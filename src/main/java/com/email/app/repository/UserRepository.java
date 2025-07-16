package com.email.app.repository;

import com.email.app.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);

    User getByEmail(@Email(message = "Please provide the valid main id") String email);
}
