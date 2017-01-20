package org.tim.dto;

/**
 * @author Timur Nasibullin
 * @since 1/20/2017
 */
public class NoteDto {
    private Long id;
    private String header;
    private String body;

    public NoteDto() {
    }

    public NoteDto(Long id, String header, String body) {
        this.id = id;
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
}
