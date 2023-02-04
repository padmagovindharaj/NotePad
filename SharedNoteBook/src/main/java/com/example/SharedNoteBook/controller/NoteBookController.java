package com.example.SharedNoteBook.controller;

import java.net.URI;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.example.SharedNoteBook.exception.DataNotFoundException;
import com.example.SharedNoteBook.model.NotePadTitle;
import com.example.SharedNoteBook.model.NotePadTopic;

import jakarta.validation.Valid;

@RestController
public class NoteBookController {
    
    @Autowired
    private NotePadService service;

    //rest api's for title

    @PostMapping("/addTitle")
    public ResponseEntity<String> saveTitles(@Valid @RequestBody NotePadTitle title) {
        NotePadTitle savedTitle = service.saveTitles(title);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTitle.getId())
                        .toUri();
        return ResponseEntity.created(location).body("Title : " + title.getTitle() + " is created successfully");
    }

    @GetMapping("/getTitle")
    public List<NotePadTitle> getTitles() {
        List<NotePadTitle> title = service.getTitles();
        if(title.isEmpty())
        {
            throw new DataNotFoundException("No title is present");
        }
        return title;
    }

    @GetMapping("/getTitle/{id}")
    public EntityModel<NotePadTitle> getTitleById(@PathVariable Long id) {
        NotePadTitle title = service.getTilteById(id);
        if(title == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        
        EntityModel<NotePadTitle> entityModel = EntityModel.of(title);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getTopicsUnderTitle(id));
        entityModel.add(link.withRel("all-toipc"));

        return entityModel;
    }

    @PutMapping("/editTitle/{id}")
    public ResponseEntity<String> updateTitle(@Valid @RequestBody NotePadTitle title, @PathVariable Long id) {
        NotePadTitle getTitle = service.getTilteById(id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present to update ");
        }
        service.updateTitle(title, id);
        return ResponseEntity.ok(getTitle.getTitle() + " is updated to " + title.getTitle() + " successfully");
    }

    @DeleteMapping("/deleteTitle/{id}")
    public ResponseEntity<String> deleteTitle(@PathVariable Long id) {
        NotePadTitle getTitle = service.getTilteById(id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present to delete");
        }
        service.deleteTitle(id);
        return ResponseEntity.ok(getTitle.getTitle() + " is deleted successfully");
    }

    //rest api's for topic

    @PostMapping("/title/{id}/addTopic")
    public ResponseEntity<String> addTopicsUnderTitle(@Valid @RequestBody NotePadTopic topic, @PathVariable Long id) {
        NotePadTitle getTitle = service.getTilteById(id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present to add the topic");
        }
        NotePadTopic savedTopic = service.saveTopicsUnderTitle(topic, id);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTopic.getId())
                        .toUri();
        return ResponseEntity.created(location).body("Title : " + topic.getTopic() + " is created successfully");
    }

    @GetMapping("/title/{id}/getTopic")
    public List<NotePadTopic> getTopicsUnderTitle(@PathVariable Long id) {
        NotePadTitle getTitle = service.getTilteById(id);
        List<NotePadTopic> topics = service.getTopicsUnderTitle(id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        else if (topics.isEmpty())
        {
            throw new DataNotFoundException("There is No topics is present under this " + getTitle.getTitle() + " title");
        }
        return topics;
    }

    @GetMapping("/title/{title_id}/getTopic/{topic_id}")
    public NotePadTopic getSingleTopicUnderTitle(@PathVariable Long title_id, @PathVariable Long topic_id) {
        NotePadTitle getTitle = service.getTilteById(title_id);
        NotePadTopic topics = service.getTopicUnderTitleById(title_id, topic_id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        else if (topics == null)
        {
            throw new DataNotFoundException("There is No topics is present under this " + getTitle.getTitle() + " title");
        }
        return service.getTopicUnderTitleById(title_id, topic_id);
    }

    @PutMapping("/title/{title_id}/editTopic/{topic_id}")
    public ResponseEntity<String> updateTopicUnderTitle(@Valid @RequestBody NotePadTopic topic, @PathVariable Long title_id, @PathVariable Long topic_id) {
        NotePadTitle getTitle = service.getTilteById(title_id);
        NotePadTopic topics = service.getTopicUnderTitleById(title_id, topic_id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        else if (topics == null)
        {
            throw new DataNotFoundException("There is No topics is present under this " + getTitle.getTitle() + " title");
        }
        service.updateTopic(topic, title_id, topic_id);
        return ResponseEntity.ok(topics.getTopic() + " is updated to " + topics.getTopic() + " successfully");
    }

    @DeleteMapping("/title/{title_id}/deleteTopic")
    public ResponseEntity<String> deleteAllTopicUnderTitle(@PathVariable Long title_id) {
        NotePadTitle getTitle = service.getTilteById(title_id);
        List<NotePadTopic> topics = service.getTopicsUnderTitle(title_id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        else if (topics.isEmpty())
        {
            throw new DataNotFoundException("There is No topics is present under this " + getTitle.getTitle() + " title");
        }
        service.deleteAllTopicUnderTitle(title_id);
        return ResponseEntity.ok("Topics under this '" + getTitle.getTitle() + "' title is deleted successfully");
    }

    @DeleteMapping("/title/{title_id}/deleteTopic/{toipc_id}")
    public ResponseEntity<String> deleteTopicUnderTitle(@PathVariable Long title_id, @PathVariable Long toipc_id) {
        NotePadTitle getTitle = service.getTilteById(title_id);
        NotePadTopic topic = service.getTopicUnderTitleById(title_id, toipc_id);
        if(getTitle == null)
        {
            throw new DataNotFoundException("There is No title is present");
        }
        else if (topic == null)
        {
            throw new DataNotFoundException("There is No topic is present under this " + getTitle.getTitle() + " title");
        }
        service.deleteTopicUnderTitleById(title_id, toipc_id);
        return ResponseEntity.ok("The Topic : '"+ topic.getTopic() +"' under this '" + getTitle.getTitle() + "' title is deleted successfully");
    }
}