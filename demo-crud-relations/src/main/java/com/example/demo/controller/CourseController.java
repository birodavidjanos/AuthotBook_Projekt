package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService cs){
        this.courseService = cs;
    }

    @GetMapping
    public String list(Model m){
        m.addAttribute("courses", courseService.findAll());
        return "courses/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model m){
        m.addAttribute("course", new Course());
        return "courses/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String save(@ModelAttribute Course course){
        courseService.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model m){
        m.addAttribute("course", courseService.findById(id));
        return "courses/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model m){
        m.addAttribute("course", courseService.findById(id));
        return "courses/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        courseService.delete(id);
        return "redirect:/courses";
    }
}
