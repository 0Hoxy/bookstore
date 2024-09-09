package com.hoxy.bookstore.service;

import com.hoxy.bookstore.entity.Author;
import com.hoxy.bookstore.entity.Book;
import com.hoxy.bookstore.entity.Category;
import com.hoxy.bookstore.entity.Stock;
import com.hoxy.bookstore.repository.AuthorRepository;
import com.hoxy.bookstore.repository.BookRepository;
import com.hoxy.bookstore.repository.CategoryRepository;
import com.hoxy.bookstore.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@ActiveProfiles("test") //used test properties
class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StockRepository stockRepository;

    @Test
    void testSaveBook() {
        Author author = new Author();
        author.setName("DM Kim");
        author.setBiography("alive");
        authorRepository.save(author);

        Category category = new Category();
        category.setName("Novel");
        categoryRepository.save(category);

        Stock stock = new Stock();
        stock.setQuantity(10);
        stockRepository.save(stock);

        Book book = new Book();
        book.setTitle("test book");
        book.setIsbn("123-456-789");
        book.setPrice(20000);
        book.setAuthor(author);
        book.setCategory(category);
        book.setStock(stock);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook.getId(), "책 ID는 저장된 후 null이 아니어야 합니다.");
        assertEquals("test book", savedBook.getTitle(), "책 제목이 일치해야합니다.");
        assertEquals("DM Kim", savedBook.getAuthor().getName(), "저자의 이름이 일치해야 합니다.");
        assertEquals("Novel", savedBook.getCategory().getName(), "카테고리가 일치 해야합니다.");
        assertEquals(10, savedBook.getStock().getQuantity(), "수량이 일치해야 합니다.");

    }
}