package com.email.app.repository;

import com.email.app.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation,Long> {
        Confirmation findByToken(String token);

        void deleteById(long id);
}
