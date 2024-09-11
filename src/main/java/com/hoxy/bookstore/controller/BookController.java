package com.hoxy.bookstore.controller;

import com.hoxy.bookstore.dto.BookDTO;
import com.hoxy.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "책 CRUD API")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @Tag(name = "Book API")
    @Operation(summary = "책 등록", description = "책을 등록합니다")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBookDTO = bookService.saveBook(bookDTO);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @Tag(name = "Book API")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Tag(name = "Book API")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        return ResponseEntity.ok(bookDTO);
    }
        /* 책이 존재하지 않을 경우 404 Not Found를 반환하는 처리가 필요하다. Optional을 사용하거나 예외 처리를 추가할 수 있다.
        if(bookDTO == null){
            return ResponseEntity.notFound().build();
        }
        */


    @PutMapping("/{id}")
    @Tag(name = "Book API")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        //@RequestBody를 이용해서 클라이언트로 부터 요청 본문으로 전달된 데이터를 사용하여 책 정보를 업데이트 함
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBookDTO);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Book API")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
        //삭제가 성공적으로 이루어졌을 때  204 No Content를 반환하고, 본문 없음
        //개선점: 삭제할 책이 존재하지 않는 경우 404 Not Found를 반환 처리할 필요가 있음, 삭제 전 해당 책이 존재하는지 확인하는 로직 추가
        /*
        if(!bookService.findBookById(id)){
            return ResponseEntity.notFound().build();
        }
        */
    }
}
