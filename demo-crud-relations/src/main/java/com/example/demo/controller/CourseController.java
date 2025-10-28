package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService cs){ this.courseService = cs; }

    @GetMapping
    public String list(Model m){
        m.addAttribute("courses", courseService.findAll());
        return "courses/list";
    }

    @GetMapping("/new")
    public String createForm(Model m){
        m.addAttribute("course", new Course());
        return "courses/form";
    }

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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model m){
        m.addAttribute("course", courseService.findById(id));
        return "courses/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        courseService.delete(id);
        return "redirect:/courses";
    }
}
