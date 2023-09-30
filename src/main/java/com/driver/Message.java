package com.driver;

import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class Message {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Message() {

    }

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
    }

    private int id;
    private String content;

    @CreatedDate
    private Date timestamp;
}
