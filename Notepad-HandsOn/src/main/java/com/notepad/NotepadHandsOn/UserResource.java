package com.notepad.NotepadHandsOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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


//@RestController
public class UserResource {
    private UsersDAO service;

    public UserResource(UsersDAO service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return service.findAll();
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
        Users getUser = service.findOne(id);
        if(getUser == null)
        {
            throw new UserNotFoundException("Id="+id);
        }
        EntityModel<Users> entityModel = EntityModel.of(getUser);
        
        WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).getAllUsers());
        entityModel.add(link.withRel("all-users"));
        
        return entityModel;
    }
    
    @PostMapping("/users")
    public ResponseEntity<Users> postUser(@Valid @RequestBody Users user) {
        Users savedUser = service.saveUser(user);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable int id) {
        service.deleteOne(id);
    }
}
