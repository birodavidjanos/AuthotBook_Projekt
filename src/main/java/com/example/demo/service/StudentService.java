package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository repo){ this.repo = repo; }
    public List<Student> findAll(){ return repo.findAll(); }
    public Student findById(Long id){ return repo.findById(id).orElseThrow(); }
    public Student save(Student s){ return repo.save(s); }
    public void delete(Long id){ repo.deleteById(id); }
}
