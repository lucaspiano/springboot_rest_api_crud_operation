package com.avenuecode.springbootrestjsonapp.domain.dto;

import java.io.Serializable;

public class BookDTO implements Serializable {
    private Long id;
    private String title;
    private String author;
    private String publishing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublish(String publishing) {
        this.publishing = publishing;
    }
}
