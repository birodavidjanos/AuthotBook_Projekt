package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.example.demo.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService s, CourseService c){
        this.studentService = s;
        this.courseService = c;
    }

    @GetMapping
    public String list(Model m){
        m.addAttribute("students", studentService.findAll());
        return "students/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model m){
        m.addAttribute("student", new Student());
        m.addAttribute("courses", courseService.findAll());
        return "students/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String save(@ModelAttribute Student student, @RequestParam(required = false) List<Long> courseIds){
        student.getCourses().clear();
        if(courseIds != null) {
            courseIds.stream().map(courseService::findById).forEach(student::addCourse);
        }
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model m){
        m.addAttribute("student", studentService.findById(id));
        return "students/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model m){
        m.addAttribute("student", studentService.findById(id));
        m.addAttribute("courses", courseService.findAll());
        return "students/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        studentService.delete(id);
        return "redirect:/students";
    }
}
