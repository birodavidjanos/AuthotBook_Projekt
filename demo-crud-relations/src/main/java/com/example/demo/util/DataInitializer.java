package com.example.demo.util;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(AuthorRepository authorRepo, BookRepository bookRepo,
                           StudentRepository studentRepo, CourseRepository courseRepo,
                           AppUserRepository userRepo, AppRoleRepository roleRepo,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            AppRole rAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new AppRole("ROLE_ADMIN")));
            AppRole rUser = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(new AppRole("ROLE_USER")));

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.getRoles().add(rAdmin);
            admin.getRoles().add(rUser);
            userRepo.save(admin);

            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.getRoles().add(rUser);
            userRepo.save(user);

            Author a1 = new Author();
            a1.setName("Alice Author");
            a1.setBio("Bio of Alice");
            authorRepo.save(a1);

            Book b1 = new Book();
            b1.setTitle("Spring in Action");
            b1.setIsbn("ISBN-001");
            b1.setAuthor(a1);
            bookRepo.save(b1);

            Book b2 = new Book();
            b2.setTitle("Hibernate Basics");
            b2.setIsbn("ISBN-002");
            b2.setAuthor(a1);
            bookRepo.save(b2);

            Course c1 = new Course();
            c1.setTitle("Mathematics");
            c1.setCode("MATH101");
            courseRepo.save(c1);

            Course c2 = new Course();
            c2.setTitle("Physics");
            c2.setCode("PHYS101");
            courseRepo.save(c2);

            Student s1 = new Student();
            s1.setName("John Student");
            s1.setEmail("john@example.com");
            s1.addCourse(c1);
            s1.addCourse(c2);
            studentRepo.save(s1);

            Student s2 = new Student();
            s2.setName("Jane Student");
            s2.setEmail("jane@example.com");
            s2.addCourse(c1);
            studentRepo.save(s2);
        };
    }
}
