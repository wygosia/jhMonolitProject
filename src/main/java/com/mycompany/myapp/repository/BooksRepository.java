package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Books;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Books entity.
 */
@SuppressWarnings("unused")
public interface BooksRepository extends JpaRepository<Books,Long> {

    @Query("select books from Books books where books.user.login = ?#{principal.username}")
    List<Books> findByUserIsCurrentUser();

}
