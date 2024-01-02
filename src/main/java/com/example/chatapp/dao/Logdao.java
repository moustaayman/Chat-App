package com.example.chatapp.dao;

import com.example.chatapp.models.Log;

public interface Logdao {
    void saveLog(Log log);
    void updateLog(Log log);
}
