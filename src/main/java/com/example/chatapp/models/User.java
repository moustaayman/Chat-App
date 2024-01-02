package com.example.chatapp.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int USER_ID;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "FIRST_NAME")
    private String firstname;
    @Column(name = "LAST_NAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PERMISSION")
    private PermissionType permission;
    @Column(name = "LAST_CONNECTION_TIME")
    private Timestamp lastConnectionTime;
    @Column(name = "STATUS")
    private Status status;

    public User() {
    }
    public enum Status {
        ONLINE(0),
        OFFLINE(1),
        BANNED(2);
        private final int value;
        Status(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public enum PermissionType {
        NORMAL_USER(0),
        MODERATOR(1),
        ADMINISTRATOR(2);
        private final int value;
        PermissionType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public PermissionType getPermission() {
        return permission;
    }
    public void setPermission(PermissionType permission) {
        this.permission = permission;
    }
    public Timestamp getLastConnectionTime() {
        return lastConnectionTime;
    }
    public void setLastConnectionTime(Timestamp lastConnectionTime) {
        this.lastConnectionTime = lastConnectionTime;
    }

    @Override
    public String toString() {
        return "{" +
                "USER_ID=" + USER_ID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastConnectionTime=" + lastConnectionTime +
                // Add other fields as needed
                '}';
    }
}