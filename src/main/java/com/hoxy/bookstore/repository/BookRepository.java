package com.hoxy.bookstore.repository;

import com.hoxy.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(Long id);
}
