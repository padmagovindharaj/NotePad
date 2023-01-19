package com.notepad.NotepadHandsOn.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notepad.NotepadHandsOn.database.notepadService;
import com.notepad.NotepadHandsOn.model.notepadModel;

@RestController
public class notepadController {

    @Autowired
    public notepadService service;

    @PostMapping("/")
    public void saveNotes(@RequestBody notepadModel notes){
        service.saveNotes(notes);
    }

    @GetMapping("/getNotes")
    public List<notepadModel> getNotes(){
        List<notepadModel> notes=service.getNotes();
        return notes;
        
    }

    public notepadModel getNote(long id){
        return service.getNote(id);

    }

    @PutMapping("/updateNotes/{id}")
    public void editNotes(@RequestBody notepadModel note,@PathVariable("id") long id){
        service.editNote(note,id);
        

    }

    @DeleteMapping("/deleteNote/{id}")
    public void editNote(@PathVariable("id") long id){
         service.deleteNote(id);
    }
    
}
