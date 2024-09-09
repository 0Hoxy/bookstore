package com.hoxy.bookstore.service;

import com.hoxy.bookstore.dto.AuthorDTO;
import com.hoxy.bookstore.dto.BookDTO;
import com.hoxy.bookstore.dto.CategoryDTO;
import com.hoxy.bookstore.dto.StockDTO;
import com.hoxy.bookstore.entity.Author;
import com.hoxy.bookstore.entity.Book;
import com.hoxy.bookstore.entity.Category;
import com.hoxy.bookstore.entity.Stock;
import com.hoxy.bookstore.repository.AuthorRepository;
import com.hoxy.bookstore.repository.BookRepository;
import com.hoxy.bookstore.repository.CategoryRepository;
import com.hoxy.bookstore.repository.StockRepository;
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

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);

        return convertToDTO(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDTO findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 책을 찾을 수 없습니다."));
        return convertToDTO(book);
    }


    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 책을 찾을 수 없습니다."));
        bookRepository.delete(book);

        Stock stock = stockRepository.findByBook(book);
        if (stock != null) {
            stockRepository.delete(stock);
        }
    }

    @Transactional
    public BookDTO updateBook(Long id, BookDTO updateBookData) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID가 " + id + "인 책을 찾을 수 없습니다."));

        existingBook.setTitle(updateBookData.getTitle());
        existingBook.setPrice(updateBookData.getPrice());

        Author author = authorRepository.findByName(updateBookData.getAuthor().getName());
        if (author == null) {
            author = new Author();
            author.setName(updateBookData.getAuthor().getName());
            author.setBiography(updateBookData.getAuthor().getBiography());
            authorRepository.save(author);
        }
        existingBook.setAuthor(author);

        Category category = categoryRepository.findByName(updateBookData.getCategory().getName());
        if (category == null) {
            category = new Category();
            category.setName(updateBookData.getCategory().getName());
            categoryRepository.save(category);
        }
        existingBook.setCategory(category);

        Stock stock = existingBook.getStock();
        if (stock == null) {
            stock = new Stock();
            stock.setBook(existingBook);
        }
        stock.setQuantity(updateBookData.getStock().getQuantity());
        stockRepository.save(stock);

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    private Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());

        // Author 엔티티 설정
        if (bookDTO.getAuthor() != null) {
            Author author = authorRepository.findByName(bookDTO.getAuthor().getName());
            if (author == null) {
                author = new Author();
                author.setName(bookDTO.getAuthor().getName());
                author.setBiography(bookDTO.getAuthor().getBiography());
                authorRepository.save(author);
            }
            book.setAuthor(author);
        }

        // Category 엔티티 설정
        if (bookDTO.getCategory() != null) {
            Category category = categoryRepository.findByName(bookDTO.getCategory().getName());
            if (category == null) {
                category = new Category();
                category.setName(bookDTO.getCategory().getName());
                categoryRepository.save(category);
            }
            book.setCategory(category);
        }

        // Stock 엔티티 설정
        if (bookDTO.getStock() != null) {
            Stock stock = new Stock();
            stock.setQuantity(bookDTO.getStock().getQuantity());
            stock.setBook(book); // 양방향 연관 설정
            book.setStock(stock);
        }

        return book;
    }

    private BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setPrice(book.getPrice());

        if (book.getAuthor() != null) {
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(book.getAuthor().getId());
            authorDTO.setName(book.getAuthor().getName());
            authorDTO.setBiography(book.getAuthor().getBiography());
            bookDTO.setAuthor(authorDTO);
        }

        if (book.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(book.getCategory().getId());
            categoryDTO.setName(book.getCategory().getName());
            bookDTO.setCategory(categoryDTO);
        }

        if (book.getStock() != null) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setId(book.getStock().getId());
            stockDTO.setQuantity(book.getStock().getQuantity());
            bookDTO.setStock(stockDTO);
        }

        return bookDTO;
    }
}
