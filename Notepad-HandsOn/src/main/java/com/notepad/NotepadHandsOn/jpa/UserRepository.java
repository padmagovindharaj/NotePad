package com.notepad.NotepadHandsOn.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notepad.NotepadHandsOn.Users;

public interface UserRepository extends JpaRepository<Users,Integer>{
    
}
