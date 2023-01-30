package com.notepad.NotepadHandsOn.course.springDataJPA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notepad.NotepadHandsOn.course.Course;

public interface CourseSpringDataJPARepository extends JpaRepository<Course,Long>{
    
    List<Course> findByAuthor(String author);
    List<Course> findByName(String name);
}
