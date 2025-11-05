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

            // további szerzők és könyvek
            Author a2 = new Author();
            a2.setName("Bob Writer");
            a2.setBio("Technical and fiction books");
            authorRepo.save(a2);

            Book b3 = new Book();
            b3.setTitle("Effective Java");
            b3.setIsbn("ISBN-003");
            b3.setAuthor(a2);
            bookRepo.save(b3);

            Book b4 = new Book();
            b4.setTitle("Clean Code");
            b4.setIsbn("ISBN-004");
            b4.setAuthor(a2);
            bookRepo.save(b4);

            Author a3 = new Author();
            a3.setName("Clara Poet");
            a3.setBio("Novelist and poet");
            authorRepo.save(a3);

            Book b5 = new Book();
            b5.setTitle("Modern Poetry");
            b5.setIsbn("ISBN-005");
            b5.setAuthor(a3);
            bookRepo.save(b5);

// további kurzusok
            Course c3 = new Course();
            c3.setTitle("Introduction to Computer Science");
            c3.setCode("CS101");
            courseRepo.save(c3);

            Course c4 = new Course();
            c4.setTitle("English Literature");
            c4.setCode("ENG101");
            courseRepo.save(c4);

            Course c5 = new Course();
            c5.setTitle("World History");
            c5.setCode("HIST201");
            courseRepo.save(c5);

// további diákok és beiratkozások
            Student s3 = new Student();
            s3.setName("Maria Student");
            s3.setEmail("maria@example.com");
            s3.addCourse(c3);
            s3.addCourse(c1); // ha c1 korábban definiálva van (Mathematics)
            studentRepo.save(s3);

            Student s4 = new Student();
            s4.setName("Peter Student");
            s4.setEmail("peter@example.com");
            s4.addCourse(c4);
            s4.addCourse(c5);
            studentRepo.save(s4);

            Student s5 = new Student();
            s5.setName("Lucy Student");
            s5.setEmail("lucy@example.com");
            s5.addCourse(c2); // Physics
            s5.addCourse(c3);
            studentRepo.save(s5);

// opcionális: további egyszerű beiratkozás bővítés
            s2.addCourse(c3); // Jane is now also in CS101
            studentRepo.save(s2);

        };
    }
}
