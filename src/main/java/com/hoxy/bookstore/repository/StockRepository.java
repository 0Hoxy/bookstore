package com.hoxy.bookstore.repository;

import com.hoxy.bookstore.entity.Book;
import com.hoxy.bookstore.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByBook(Book book);
}
