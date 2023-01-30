package com.notepad.NotepadHandsOn;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonFilter("filterForPassowrdAndId")
@Entity(name = "USER_DETAILS")
public class Users {
    
    @Id
    @GeneratedValue
    int Id;

    @Size(min = 2, message = "name should have minimum 2 length")
    @JsonProperty("user_name")
    String name;

    @PastOrPresent(message = "birthdate should be past or present date")
    @JsonProperty("user_birthdate")
    @Column(name = "birth_date")
    LocalDate birthdate;

    @JsonIgnore
    String password;

    @OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Post> posts;

    public Users() {
    }

    public Users(int id, String name, LocalDate birthdate) {
        Id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.password = "1234";
    }
    
    public void setId(int id) {
        Id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getId() {
        return Id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    @Override
    public String toString() {
        return "Users [Id=" + Id + ", name=" + name + ", birthdate=" + birthdate + ", password=" + password + "]";
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    

}
