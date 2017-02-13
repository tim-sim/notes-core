package org.notes.core.service;

import org.notes.core.domain.NoteBook;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
public interface NoteBookRepository extends CrudRepository<NoteBook, Long> {

}
