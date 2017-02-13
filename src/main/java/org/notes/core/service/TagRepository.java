package org.notes.core.service;

import org.springframework.data.repository.CrudRepository;
import org.notes.core.domain.Tag;

/**
 * @author Timur Nasibullin
 * @since 1/24/2017
 */
public interface TagRepository extends CrudRepository<Tag, Long> {
    Tag findFirstByName(String tagName);
}
