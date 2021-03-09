package com.example.postman.model;

import javax.persistence.*;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "description")
    private String description;

    @Column(name = "noteOrder")
    private int noteOrder;

    public Note() {
    }

    public Note(String description, int noteOrder) {
        this.description = description;
        this.noteOrder = noteOrder;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoteOrder() {
        return noteOrder;
    }

    public void setNoteOrder(int noteOrder) {
        this.noteOrder = noteOrder;
    }
}
