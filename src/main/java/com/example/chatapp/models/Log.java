package com.example.chatapp.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "LOG")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int LOG_ID;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private LogType type;

    public Log() {
    }
    public enum LogType {
        CONNECTION,
        DISCONNECTION,
        MESSAGE_SENT,
        BAN,
        STATUS_UPDATE
    }

    public int getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(int LOG_ID) {
        this.LOG_ID = LOG_ID;
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

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }
}
