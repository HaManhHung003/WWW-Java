package com.yourname.bookstore.controller;

import com.yourname.bookstore.entity.Book;
import com.yourname.bookstore.service.BookService;
import com.yourname.bookstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final com.yourname.bookstore.service.BookService bookService;
    private final com.yourname.bookstore.service.OrderService orderService;

    public AdminController(BookService bookService,
            com.yourname.bookstore.service.OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String dashboard(Model model) {
        model.addAttribute("totalBooks", bookService.getAllBooks().size());
        model.addAttribute("totalOrders", orderService.getAllOrders().size());
        return "admin/dashboard";
    }

    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }



    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/books";
    }

    @GetMapping("/books/add")
    public String showAddForm(Model model) {
        Book book = new Book();
        book.setPrice(0.0);
        book.setQuantity(0);
        model.addAttribute("book", book);
        return "admin/book-form";
    }

    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/admin/books";
    }

    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "admin/book-form";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/admin/books";
    }
}
