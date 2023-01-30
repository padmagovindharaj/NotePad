package com.notepad.NotepadHandsOn.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.notepad.NotepadHandsOn.course.jdbc.CourseJDBCRepository;
import com.notepad.NotepadHandsOn.course.jpa.CourseJPARepository;
import com.notepad.NotepadHandsOn.course.springDataJPA.CourseSpringDataJPARepository;

@Component
public class CourseCommandLineRunner implements CommandLineRunner {

    // @Autowired
    // private CourseJDBCRepository repository;

    // @Autowired
    // private CourseJPARepository repository;

    @Autowired
    private CourseSpringDataJPARepository repository;

    @Override
    public void run(String... args) throws Exception {
        // repository.insert(new Course(1,"Learn Java","Ragul"));
        // repository.insert(new Course(2,"Learn Spring Boot","padma Priya"));
        // repository.insert(new Course(3,"Learn Micro services","priya dharsini"));

        repository.save(new Course(1,"Learn Java","Ragul"));
        repository.save(new Course(2,"Learn Spring Boot","padma Priya"));
        repository.save(new Course(3,"Learn Micro services","priya dharsini"));

        repository.deleteById((long) 2);

        // System.out.println(repository.selectById((long) 1));
        // System.out.println(repository.selectById((long) 3));

        System.out.println(repository.findById((long) 1));
        System.out.println(repository.findById((long) 3));

        System.out.println(repository.findAll());

        System.out.println(repository.count());

        System.out.println(repository.findByAuthor("Ragul"));
        System.out.println(repository.findByAuthor(""));

        System.out.println(repository.findByName("Learn Micro services"));
        //System.out.println();
    }
    
}
