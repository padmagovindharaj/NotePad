package com.notepad.NotepadHandsOn.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notepad.NotepadHandsOn.model.notepadModel;

public interface notepadRepository extends JpaRepository<notepadModel,Long>{
    
}
