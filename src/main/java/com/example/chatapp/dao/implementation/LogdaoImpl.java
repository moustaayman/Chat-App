package com.example.chatapp.dao.implementation;

import com.example.chatapp.dao.Logdao;
import com.example.chatapp.models.Log;
import com.example.chatapp.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LogdaoImpl implements Logdao {
    private final EntityManagerFactory entityManagerFactory;

    public LogdaoImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }
    @Override
    public void saveLog(Log log) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(log);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public void updateLog(Log log) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(log);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
