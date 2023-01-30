package com.notepad.NotepadHandsOn.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notepad.NotepadHandsOn.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
    
}
