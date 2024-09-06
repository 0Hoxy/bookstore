package com.hoxy.bookstore.service;

import com.hoxy.bookstore.entity.Book;
import com.hoxy.bookstore.repository.AuthorRepository;
import com.hoxy.bookstore.repository.BookRepository;
import com.hoxy.bookstore.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
    }

    @Transactional
    public Book updateBook(Long id, Book updateBookData) {
        // 기존 책을 데이터베이스에서 찾습니다.
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        existingBook.setTitle(updateBookData.getTitle());
        existingBook.setAuthor(updateBookData.getAuthor());
        existingBook.setPrice(updateBookData.getPrice());
        existingBook.setCategory(updateBookData.getCategory()); // authorRepo 사용으로 수정
        existingBook.setCategory(updateBookData.getCategory()); // categoryRepo 사용으로 수정
        bookRepository.save(existingBook);
        return existingBook;
    }
}
