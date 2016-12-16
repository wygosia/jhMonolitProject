package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Toys;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Toys entity.
 */
@SuppressWarnings("unused")
public interface ToysRepository extends JpaRepository<Toys,Long> {

}
