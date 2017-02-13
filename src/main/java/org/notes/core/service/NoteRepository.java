package org.notes.core.service;

import org.springframework.data.repository.CrudRepository;
import org.notes.core.domain.Note;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
public interface NoteRepository extends CrudRepository<Note, Long> {
}
