package org.example.lab6.controller;

import org.example.lab6.entity.Book;
import org.example.lab6.repository.BookRepository;
import org.example.lab6.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired private BookRepository bookRepo;
    @Autowired private CategoryRepository catRepo;

    @GetMapping
    public String list(Model model, String keyword, Long categoryId) {
        model.addAttribute("books", bookRepo.searchBooks(keyword, categoryId));
        model.addAttribute("categories", catRepo.findAll());
        return "books/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", catRepo.findAll());
        return "books/form";
    }

    @PostMapping("/save")
    public String save(Book book) {
        bookRepo.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookRepo.findById(id).orElseThrow());
        model.addAttribute("categories", catRepo.findAll());
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return "redirect:/books";
    }
}