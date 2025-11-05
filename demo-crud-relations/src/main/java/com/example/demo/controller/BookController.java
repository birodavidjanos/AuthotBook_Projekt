package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Author;
import com.example.demo.service.BookService;
import com.example.demo.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService b, AuthorService a){
        this.bookService = b;
        this.authorService = a;
    }

    @GetMapping
    public String list(Model m){
        m.addAttribute("books", bookService.findAll());
        return "books/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model m){
        m.addAttribute("book", new Book());
        m.addAttribute("authors", authorService.findAll());
        return "books/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String save(@ModelAttribute Book book, @RequestParam(required = false) Long authorId){
        // ha az id mező be van töltve a formból, a bookService.save feltételezhetően update-eli az entitást
        if (authorId != null) {
            Author author = authorService.findById(authorId);
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model m){
        m.addAttribute("book", bookService.findById(id));
        return "books/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model m){
        m.addAttribute("book", bookService.findById(id));
        m.addAttribute("authors", authorService.findAll());
        return "books/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        bookService.delete(id);
        return "redirect:/books";
    }
}
