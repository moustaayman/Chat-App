package com.example.chatapp.models;

import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import com.google.gson.Gson;
import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MESSAGE_ID;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;
    @Column(name = "CONTENT")
    private String content;

    public Message() {
    }

    public int getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(int MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
