package com.example.demo.service;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository repo;
    private final StudentRepository studentRepo;

    public CourseService(CourseRepository repo, StudentRepository studentRepo){
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    public List<Course> findAll(){ return repo.findAll(); }
    public Course findById(Long id){ return repo.findById(id).orElseThrow(); }
    public Course save(Course c){ return repo.save(c); }

    @Transactional
    public void delete(Long id){
        Course course = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));

        // másolat, mert közben módosítjuk az eredeti gyűjteményt
        List<Student> students = new ArrayList<>(course.getStudents());

        for (Student s : students) {
            s.getCourses().remove(course);    // eltávolítjuk a kapcsolatot a student oldalán (owning side)
            studentRepo.save(s);              // persistáljuk a változást
        }

        // most már biztonságosan törölhetjük a kurzust
        repo.delete(course);
    }
}
