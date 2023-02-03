package com.example.SharedNoteBook.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SharedNoteBook.model.NotePadTitle;

public interface NotePadTitleRepository extends JpaRepository<NotePadTitle,Long>{
    
}
