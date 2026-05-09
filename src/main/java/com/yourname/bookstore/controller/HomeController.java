package com.yourname.bookstore.controller;

import com.yourname.bookstore.entity.Book;
import com.yourname.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(value = "search", required = false) String search) {
        List<Book> books;
        if (search != null && !search.isEmpty()) {
            books = bookService.searchBooks(search);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "common/login";
    }
}
