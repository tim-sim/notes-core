package org.notes.core.rest;

import org.notes.core.domain.NoteBook;
import org.notes.core.domain.Tag;
import org.notes.core.dto.NoteBookDto;
import org.notes.core.dto.NoteDto;
import org.notes.core.exceptions.ObjectNotFoundException;
import org.notes.core.service.NoteRepository;
import org.notes.core.service.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.notes.core.domain.Note;
import org.notes.core.service.NoteBookRepository;

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
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/notebooks")
    public Set<NoteBookDto> getNoteBooks() {
        return StreamSupport.stream(noteBookRepository.findAll().spliterator(), false)
                .map(noteBook -> new NoteBookDto(noteBook.getId(), noteBook.getName()))
                .collect(Collectors.toSet());
    }

    @PostMapping(value = "/notebooks")
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
    public void createNote(@PathVariable("notebook_id") Long noteBookId, @RequestBody NoteDto noteDto) {
        NoteBook noteBook = noteBookRepository.findOne(noteBookId);

        if (noteBook != null) {
            Note note = new Note(noteDto.getHeader(), noteDto.getBody());
            note.setNoteBook(noteBook);
            note.setTags(resolveTags(noteDto));
            noteRepository.save(note);
        } else {
            throw new ObjectNotFoundException();
        }
    }

    @PutMapping("/notes/{note_id}")
    public void updateNote(@PathVariable("note_id") Long noteId, @RequestBody NoteDto noteDto) {
        Note note = noteRepository.findOne(noteId);
        if (note != null) {
            note.setHeader(noteDto.getHeader());
            note.setBody(noteDto.getBody());
            note.setTags(resolveTags(noteDto));
            noteRepository.save(note);
        } else {
            throw new ObjectNotFoundException();
        }
    }

    @DeleteMapping("/notes/{note_id}")
    public void deleteNote(@PathVariable("note_id") Long noteId) {
        noteRepository.delete(noteId);
    }

    private Set<Tag> resolveTags(@RequestBody NoteDto noteDto) {
        return noteDto.getTags().stream().map(tag -> resolveTag(tag)).collect(Collectors.toSet());
    }

    private Tag resolveTag(String tagName) {
        Tag tag = tagRepository.findFirstByName(tagName);

        if (tag == null) {
            tag = new Tag();
            tag.setName(tagName);
        }

        return tag;
    }
}
