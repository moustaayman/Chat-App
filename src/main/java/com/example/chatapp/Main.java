package com.example.chatapp;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.schema-generation.database.action", "create");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default", properties);

        entityManagerFactory.close();
    }
}