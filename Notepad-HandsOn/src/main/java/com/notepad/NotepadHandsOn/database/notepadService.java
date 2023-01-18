package com.notepad.NotepadHandsOn.database;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notepad.NotepadHandsOn.model.notepadModel;

@Service
public class notepadService {
    @Autowired
    public notepadRepository repository;

    public void saveNotes(notepadModel notes) {
        repository.save(notes);
    }

    public List<notepadModel> getNotes() {
        return repository.findAll();
    }

    public notepadModel getNote(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteNote(long id) {
        repository.deleteById(id);;
    }

    
}
