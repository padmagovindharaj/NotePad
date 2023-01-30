package com.notepad.NotepadHandsOn.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "COURSE_TABLE")
public class Course {

    @Id
    private long id;
    @Column(name = "COURSE_NAME")
    private String name;
    @Column(name = "AUTHOR_NAME")
    private String author;

    public Course() {
    }

    public Course(long id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    
    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", author=" + author + "]";
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    
}
