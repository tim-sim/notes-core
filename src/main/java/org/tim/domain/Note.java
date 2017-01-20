package org.tim.domain;

import javax.persistence.*;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
@Entity
@Table(name = "notes")
public class Note {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String header;
    @Column(nullable = false)
    private String body;
    @ManyToOne
    @JoinColumn(name = "notebook_id")
    private NoteBook noteBook;

    public Note() {
    }

    public Note(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
    }
}
