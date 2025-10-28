package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repo;
    public CourseService(CourseRepository repo){ this.repo = repo; }
    public List<Course> findAll(){ return repo.findAll(); }
    public Course findById(Long id){ return repo.findById(id).orElseThrow(); }
    public Course save(Course c){ return repo.save(c); }
    public void delete(Long id){ repo.deleteById(id); }
}
