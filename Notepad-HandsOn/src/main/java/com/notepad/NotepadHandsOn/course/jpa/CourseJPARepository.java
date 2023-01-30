package com.notepad.NotepadHandsOn.course.jpa;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.notepad.NotepadHandsOn.course.Course;

@Repository
@Transactional
public class CourseJPARepository {

    @Autowired
    private EntityManager entityManager;

    public void insert(Course course) {
        entityManager.merge(course);
    }

    public Course selectById(long id) {
        return entityManager.find(Course.class,id);
    }

    public void deleteById(long id) {
        Course course = selectById(id);
        entityManager.remove(course);
    }
}
