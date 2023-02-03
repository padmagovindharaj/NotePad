package com.example.SharedNoteBook.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.SharedNoteBook.database.NotePadService;
import com.example.SharedNoteBook.model.NotePadTitle;
import com.example.SharedNoteBook.model.NotePadTopic;

@RestController
public class NoteBookController {
    
    @Autowired
    private NotePadService service;

    //rest api's for title

    @PostMapping("/addTitle")
    public ResponseEntity<String> saveTitles(@RequestBody NotePadTitle title) {
        NotePadTitle savedTitle = service.saveTitles(title);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTitle.getId())
                        .toUri();
        return ResponseEntity.created(location).body("Title : " + title.getTitle() + " is created");
    }

    @GetMapping("/getTitle")
    public ResponseEntity<List<NotePadTitle>> getTitles() {
        List<NotePadTitle> titles = service.getTitles();
        if(titles.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok().body(titles);
    }

    @GetMapping("/getTitle/{id}")
    public NotePadTitle getTitleById(@PathVariable Long id) {
        return service.getTilteById(id);
    }

    @PutMapping("/editTitle/{id}")
    public void updateTitle(@RequestBody NotePadTitle title, @PathVariable Long id) {
        service.updateTitle(title, id);
    }

    @DeleteMapping("/deleteTitle/{id}")
    public void deleteTitle(@PathVariable Long id) {
        service.deleteTitle(id);
    }

    //rest api's for topic

    @PostMapping("/title/{id}/addTopic")
    public void addTopicsUnderTitle(@RequestBody NotePadTopic topic, @PathVariable Long id) {
        service.saveTopicsUnderTitle(topic, id);
    }

    @GetMapping("/title/{id}/getTopic")
    public List<NotePadTopic> getTopicsUnderTitle(@PathVariable Long id) {
        return service.getTopicsUnderTitle(id);
    }

    @GetMapping("/title/{title_id}/getTopic/{topic_id}")
    public NotePadTopic getSingleTopicUnderTitle(@PathVariable Long title_id, @PathVariable Long topic_id) {
        return service.getTopicUnderTitleById(title_id, topic_id);
    }

    @PutMapping("/title/{title_id}/editTopic/{topic_id}")
    public void updateTopicUnderTitle(@RequestBody NotePadTopic topic, @PathVariable Long title_id, @PathVariable Long topic_id) {
        service.updateTopic(topic, title_id, topic_id);
    }

    @DeleteMapping("/title/{title_id}/deleteTopic")
    public void deleteAllTopicUnderTitle(@PathVariable Long title_id) {
        service.deleteAllTopicUnderTitle(title_id);
    }

    @DeleteMapping("/title/{title_id}/deleteTopic/{toipc_id}")
    public void deleteTopicUnderTitle(@PathVariable Long title_id, @PathVariable Long toipc_id) {
        service.deleteTopicUnderTitleById(title_id, toipc_id);
    }

}