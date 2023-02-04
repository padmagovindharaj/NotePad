package com.example.SharedNoteBook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "note_pad_topic")
@Data
public class NotePadTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotEmpty(message = "topic must not be empty")
    private String topic;
    @Column
    @NotEmpty(message = "content must not be empty")
    private String content;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private NotePadTitle title;
    
}
