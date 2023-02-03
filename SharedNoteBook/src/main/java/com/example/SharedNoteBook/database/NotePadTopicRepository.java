package com.example.SharedNoteBook.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SharedNoteBook.model.NotePadTopic;

public interface NotePadTopicRepository extends JpaRepository<NotePadTopic, Long>{
    
}
