package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PersistentToken;
import com.mycompany.myapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepositorytory;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
