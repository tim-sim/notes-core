package org.tim.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
@Entity()
@Table(name = "notebooks")
public class NoteBook {
    @Id @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "noteBook")
    private Set<Note> notes;

    public NoteBook() {
    }

    public NoteBook(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
}
