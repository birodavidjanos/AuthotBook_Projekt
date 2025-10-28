package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService){ this.authorService = authorService; }

    @GetMapping
    public String list(Model m){
        m.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/new")
    public String createForm(Model m){
        m.addAttribute("author", new Author());
        return "authors/form";
    }

    @PostMapping
    public String save(@ModelAttribute Author author){
        authorService.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model m){
        m.addAttribute("author", authorService.findById(id));
        return "authors/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model m){
        m.addAttribute("author", authorService.findById(id));
        return "authors/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        authorService.delete(id);
        return "redirect:/authors";
    }
}
