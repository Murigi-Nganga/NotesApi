package com.example.notesapi.controller;

import com.example.notesapi.domain.CreateNoteDTO;
import com.example.notesapi.domain.Note;
import com.example.notesapi.domain.PatchNoteDTO;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.service.NoteService;
import com.example.notesapi.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/note")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService,
                          NoteRepository noteRepository) {
        this.noteService = noteService;
    }

    /**
     * @param userId The unique id of the user
     * @return a {@code ResponseEntity<List<Note>>} representing the list of notes.
     */
    @GetMapping(path = {"/{userId}/all", "/{userId}/all/"})
    public ResponseEntity<List<Note>> getAllUserNotes(@PathVariable Long userId) {
        List<Note> notes = noteService.getAllUserNotes(userId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping(path = {"/all", "/all/"})
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @PostMapping(path = {"", "/"})
    public ResponseEntity<Note> createNote(@RequestBody CreateNoteDTO createNoteDTO) {
        Note createdNote;

        try {
            createdNote = noteService.createNote(createNoteDTO);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(201).body(createdNote);
    }

    /**
     * @param noteId The unique id of the note
     * @return a {@code ResponseEntity<Note>} representing a note.
     */
    @GetMapping(path = {"/{noteId}", "/{noteId}/"})
    public ResponseEntity<Object> getSingleNote(@PathVariable Long noteId) {
        Optional<Note> note = noteService.getSingleNote(noteId);

        return note.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(path = {"/{noteId}", "/{noteId}/"})
    public ResponseEntity<Note> updateNote(@PathVariable Long noteId, @RequestBody PatchNoteDTO patchNoteDTO) {
        Note updatedNote;
        try {
            updatedNote = noteService.updateNote(noteId, patchNoteDTO);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping(path = {"/{noteId}", "/{noteId}/"})
    public void deleteNote(@PathVariable Long noteId) {
       noteService.deleteNote(noteId);
    }
}
