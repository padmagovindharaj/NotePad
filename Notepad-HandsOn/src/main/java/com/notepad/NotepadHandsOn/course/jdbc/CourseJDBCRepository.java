package com.notepad.NotepadHandsOn.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.notepad.NotepadHandsOn.course.Course;

@Repository
public class CourseJDBCRepository {

    @Autowired
    private JdbcTemplate springJdbcTemplate;
    private static String INSER_QUERY = """
            insert into course(id,name,author) values(?,?,?)
            """;
    private static String DELETE_QUERY = """
            delete from course where id = ?
            """;
    private static String SELECT_QUERY = """
            select * from course where id = ?
            """;

    public void insert(Course course) {
        springJdbcTemplate.update(INSER_QUERY,course.getId(),course.getName(),course.getAuthor());
    }

    public void deleteById(Long id) {
        springJdbcTemplate.update(DELETE_QUERY,id);
    }

    public Course selectById(Long id) {
        return springJdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
    }
}
