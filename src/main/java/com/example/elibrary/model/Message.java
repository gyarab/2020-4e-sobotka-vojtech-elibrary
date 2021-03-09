package com.example.elibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("messages")
public class Message {
    @Id
    public String id;

    private String bookId;
    private String userId;
    private String content;

    private String getId() {
        return id;
    }

    public Message() {
    }

    public Message(String bookId, String userId, String content) {
        this.bookId = bookId;
        this.userId = userId;
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
