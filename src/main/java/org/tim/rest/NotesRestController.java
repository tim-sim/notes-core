package org.tim.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tim.domain.Note;
import org.tim.domain.NoteBook;
import org.tim.dto.NoteBookDto;
import org.tim.dto.NoteDto;
import org.tim.service.NoteBookRepository;
import org.tim.service.NoteRepository;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
@RestController
public class NotesRestController {
    @Autowired
    private NoteBookRepository noteBookRepository;
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/notebooks")
    public Set<NoteBookDto> getNoteBooks() {
        return StreamSupport.stream(noteBookRepository.findAll().spliterator(), false)
                .map(noteBook -> new NoteBookDto(noteBook.getId(), noteBook.getName()))
                .collect(Collectors.toSet());
    }

    @GetMapping("/notebooks/{notebook_id}")
    public NoteBookDto getNoteBook(@PathVariable("notebook_id") Long noteBookId) {
        NoteBook noteBook = noteBookRepository.findOne(noteBookId);

        if (noteBook != null) {
            return new NoteBookDto(noteBook.getId(), noteBook.getName());
        }

        return null;
    }

    @PostMapping("/notebooks")
    public NoteBookDto createNoteBook(@RequestBody NoteBookDto noteBookDto) {
        NoteBook noteBook = noteBookRepository.save(new NoteBook(noteBookDto.getName()));
        return new NoteBookDto(noteBook.getId(), noteBook.getName());
    }

    @GetMapping("/notebooks/{notebook_id}/notes")
    public Set<NoteDto> getNotes(@PathVariable("notebook_id") Long noteBookId, @RequestParam(value = "q", required = false) String query) {
        NoteBook noteBook = noteBookRepository.findOne(noteBookId);

        if (noteBook != null) {
            return noteBook.getNotes().stream()
                    .map(note -> new NoteDto(note.getId(), note.getHeader(), note.getBody()))
                    .collect(Collectors.toSet());
        }

        return Collections.EMPTY_SET;
    }

    @PostMapping("/notebooks/{notebook_id}/notes")
    public void createNote(@PathVariable("notebook_id") Long noteBookdId, @RequestBody NoteDto noteDto) {
        NoteBook noteBook = noteBookRepository.findOne(noteBookdId);

        if (noteBook != null) {
            Note note = new Note(noteDto.getHeader(), noteDto.getBody());
            note.setNoteBook(noteBook);
            noteRepository.save(note);
        }
    }
}
