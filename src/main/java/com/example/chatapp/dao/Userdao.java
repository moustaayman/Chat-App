package com.example.chatapp.dao;

import com.example.chatapp.models.User;

import java.util.List;

public interface Userdao {
    void saveUser(User message);

    void updateUser(User message);

    void deleteUser(User message);

    User findUserById(int id);
    User findUserByEmail(String email);

    List<User> findAllUsers();

}
