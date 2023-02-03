package com.example.SharedNoteBook.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SharedNoteBook.model.NotePadTitle;
import com.example.SharedNoteBook.model.NotePadTopic;

@Service
public class NotePadService {
    
    @Autowired
    private NotePadTitleRepository notePadTitleRepository;
    @Autowired
    private NotePadTopicRepository notePadTopicRepository;

    //services method for title

    public NotePadTitle saveTitles(NotePadTitle title) {
        return notePadTitleRepository.save(title);
    }

    public List<NotePadTitle> getTitles() {
        return notePadTitleRepository.findAll();
    }

    public NotePadTitle getTilteById(Long id) {
        return notePadTitleRepository.findById(id).orElse(null);
    }

    //need to add confition to delete : if title has topic table
    public void deleteTitle(Long id) {
        notePadTitleRepository.deleteById(id);
    }

    public void updateTitle(NotePadTitle title, Long id) {
        title.setId(id);
        notePadTitleRepository.save(title);
    }

    // services method for topics

    public void saveTopicsUnderTitle(NotePadTopic topic, Long id) {
        NotePadTitle title = getTilteById(id);
        topic.setTitle(title);
        notePadTopicRepository.save(topic);
    }

    public List<NotePadTopic> getTopicsUnderTitle(Long id) {
        NotePadTitle title = getTilteById(id);
        return title.getTopic();
    }

    public NotePadTopic getTopicUnderTitleById(Long title_id, Long topic_id) {
        NotePadTitle title = getTilteById(title_id);
        for(int i=0; i < title.getTopic().size(); i=i+1)
        {
            if(title.getTopic().get(i).getId() == topic_id)
            {
                return title.getTopic().get(i);
            }
        }
        return null;
    }

    public void deleteAllTopicUnderTitle(Long title_id) {
        NotePadTitle title = getTilteById(title_id);
        for(int i=0; i < title.getTopic().size(); i=i+1)
        {
            notePadTopicRepository.deleteById(title.getTopic().get(i).getId());
        }
    }

    public void deleteTopicUnderTitleById(Long title_id, Long topic_id) {
        notePadTopicRepository.deleteById(topic_id);
    }

    public void updateTopic(NotePadTopic topic, Long title_id, Long topic_id) {
        NotePadTitle title = getTilteById(title_id);
        topic.setTitle(title);
        topic.setId(topic_id);
        notePadTopicRepository.save(topic);
    }

}
