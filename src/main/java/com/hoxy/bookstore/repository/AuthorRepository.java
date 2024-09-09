package com.hoxy.bookstore.repository;

import com.hoxy.bookstore.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    //    Optional<Author> findByName(String name);
    Author findByName(String name);
}
