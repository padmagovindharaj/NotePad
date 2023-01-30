package com.notepad.NotepadHandsOn.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.notepad.NotepadHandsOn.Post;
import com.notepad.NotepadHandsOn.UserNotFoundException;
import com.notepad.NotepadHandsOn.Users;
import com.notepad.NotepadHandsOn.UsersDAO;
import com.notepad.NotepadHandsOn.jpa.PostRepository;
import com.notepad.NotepadHandsOn.jpa.UserRepository;


@RestController
public class UserResource {
    private UsersDAO service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PostRepository postRepository;

    public UserResource(UsersDAO service, UserRepository repository, PostRepository postRepository) {
        this.service = service;
        this.repository = repository;
        this.postRepository = postRepository;
    }

    protected UserResource() {
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/users-pwd")
    public MappingJacksonValue getAllUsersPWD() {
        List<Users> allUsers =  service.findAll();
        
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(allUsers);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("user_name","user_birthdate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("filterForPassowrdAndId", filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;        
    }
    
    @GetMapping("/users/{id}")
    public EntityModel<Users> getOneUser(@PathVariable int id) {
        Optional<Users> getUser = repository.findById(id);
        if(getUser.isEmpty())
        {
            throw new UserNotFoundException("Id="+id);
        }
        EntityModel<Users> entityModel = EntityModel.of(getUser.get());
        
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllUsers());
        entityModel.add(link.withRel("all-users"));
        
        return entityModel;
    }
    
    @PostMapping("/users")
    public ResponseEntity<Users> postUser(@Valid @RequestBody Users user) {
        Users savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable int id) {
        repository.deleteById(id);
    }

    @GetMapping("/users/{id}/post")
    public List<Post> getPostForUser(@PathVariable int id) {
        Optional<Users> getUser = repository.findById(id);
        if(getUser.isEmpty())
        {
            throw new UserNotFoundException("Id="+id);
        }
        return getUser.get().getPosts();
    }

    @PostMapping("/users/{id}/post")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
        Optional<Users> getUser = repository.findById(id);
        if(getUser.isEmpty())
        {
            throw new UserNotFoundException("Id="+id);
        }
        post.setUser(getUser.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPost.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }
}
