package com.hoxy.bookstore.controller;

import com.hoxy.bookstore.dto.BookDTO;
import com.hoxy.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Tag(name = "Book Web", description = "책 웹 페이지 컨트롤러")

public class ManegeController {
    @Autowired
    private BookService bookService;
    @GetMapping
    @Tag(name = "Book Web")
    public String index(Model model) {
        List<BookDTO> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "book"; // 템플릿 이름
    }

}
