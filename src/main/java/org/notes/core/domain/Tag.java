package org.notes.core.domain;

import javax.persistence.*;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
@Entity
@Table(name = "tags")
public class Tag {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

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
}
