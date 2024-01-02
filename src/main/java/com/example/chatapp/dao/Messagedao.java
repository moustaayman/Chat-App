package com.example.chatapp.dao;

import com.example.chatapp.models.Message;

import java.util.List;

public interface Messagedao {
    void saveMessage(Message message);

    void updateMessage(Message message);

    void deleteMessage(Message message);

    Message findMessageById(Long id);

    List<Message> findAllMessages();

}