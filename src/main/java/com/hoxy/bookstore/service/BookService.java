package com.hoxy.bookstore.service;

import com.hoxy.bookstore.entity.Author;
import com.hoxy.bookstore.entity.Book;
import com.hoxy.bookstore.entity.Category;
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
    public Book saveBook(Book book) {
        return bookRepository.save(book);
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
        // Find the existing book in the database
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        // Update title and price
        existingBook.setTitle(updateBookData.getTitle());
        existingBook.setPrice(updateBookData.getPrice());

        // Update author using authorRepository
        Author author = authorRepository.findById(updateBookData.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + updateBookData.getAuthor().getId()));
        existingBook.setAuthor(author);

        // Update category using categoryRepository
        Category category = categoryRepository.findById(updateBookData.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + updateBookData.getCategory().getId()));
        existingBook.setCategory(category);

        // Save updated book to the repository
        bookRepository.save(existingBook);

        return existingBook;
    }

}
