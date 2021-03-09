package com.example.postman.controller;


import com.example.postman.model.Note;
import com.example.postman.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        try{
            List<Note> noteList = new ArrayList<>();
            noteList = noteRepository.findAll();
            return new ResponseEntity<>(noteList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try{
            if(note != null){
                Note newNote = noteRepository.save(new Note(note.getDescription(), note.getNoteOrder()));
                return new ResponseEntity<>(newNote,HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Note> reOrderNote(@RequestBody Note note) {
        try{
            if(note != null && note.getNoteOrder() > 0){
                int newOrder = note.getNoteOrder();
                List<Note> notesList = new ArrayList<>();
                notesList = noteRepository.findAll();
                Optional<Note> data = noteRepository.findById(note.getId());
                Note _note = data.get();
                if(!notesList.isEmpty()){
                    for(Note n: notesList){
                        if(newOrder > _note.getNoteOrder()){
                            if(n.getNoteOrder()>=_note.getNoteOrder() && n.getNoteOrder()<=newOrder){
                                n.setNoteOrder(n.getNoteOrder()-1);
                            }
                        }else{
                            if(n.getNoteOrder()<=_note.getNoteOrder() && n.getNoteOrder()>=newOrder){
                                n.setNoteOrder(n.getNoteOrder()+1);
                            }
                        }
                    }
                    _note.setNoteOrder(note.getNoteOrder());
                    noteRepository.saveAll(notesList);
                    return new ResponseEntity<>(_note,HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
