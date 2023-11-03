package com.example.notesapi.service;

import com.example.notesapi.domain.CreateNoteDTO;
import com.example.notesapi.domain.Note;
import com.example.notesapi.domain.PatchNoteDTO;
import com.example.notesapi.repository.NoteRepository;
import com.example.notesapi.domain.User;
import com.example.notesapi.repository.UserRepository;
import com.example.notesapi.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;


    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> getAllUserNotes(Long userId) {
        return noteRepository.findAllByUserId(userId);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getSingleNote(Long noteId) {
        return noteRepository.findById(noteId);
    }

    public Note createNote(CreateNoteDTO createNoteDTO) throws CustomException {
        Optional<User> user = userRepository.findById(createNoteDTO.userId());

        if (user.isPresent())
        {
            System.out.println(user.get().getFirstName());
            Note note = new Note(
                    user.get(),
                    createNoteDTO.title(),
                    createNoteDTO.content()
            );
            System.out.println(createNoteDTO.title());
            return noteRepository.save(note);
        }
        throw new CustomException("User with ID " + createNoteDTO.userId() + " doesn't exist");
    }

    public Note updateNote(Long noteId, PatchNoteDTO patchNoteDTO) throws CustomException {
        Optional<Note> note = noteRepository.findById(noteId);

        if(note.isEmpty()) {
            throw new CustomException("Note with Id" + noteId + "doesn't exist");
        }

        Note noteObj = note.get();

        if(patchNoteDTO.title() != null) {
            noteObj.setTitle(patchNoteDTO.title());
        }
        if(patchNoteDTO.content() != null) {
            noteObj.setContent(patchNoteDTO.content());
        }

        return noteRepository.save(noteObj);
    }

    public void deleteNote(Long noteId) {noteRepository.deleteById(noteId);}

}
