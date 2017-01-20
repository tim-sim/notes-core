package org.tim.service;

import org.springframework.data.repository.CrudRepository;
import org.tim.domain.Note;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
public interface NoteRepository extends CrudRepository<Note, Long> {
}
