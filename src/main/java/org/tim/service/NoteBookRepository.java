package org.tim.service;

import org.springframework.data.repository.CrudRepository;
import org.tim.domain.NoteBook;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
public interface NoteBookRepository extends CrudRepository<NoteBook, Long> {

}
