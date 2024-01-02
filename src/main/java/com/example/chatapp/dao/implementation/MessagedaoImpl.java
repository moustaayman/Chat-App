package com.example.chatapp.dao.implementation;

import com.example.chatapp.dao.Messagedao;
import com.example.chatapp.models.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class MessagedaoImpl implements Messagedao{
    private final EntityManagerFactory entityManagerFactory;

    public MessagedaoImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void saveMessage(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(message);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateMessage(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(message);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteMessage(Message message) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(message));
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Message findMessageById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Message.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Message> findAllMessages() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Message> query = cb.createQuery(Message.class);
            Root<Message> root = query.from(Message.class);
            query.select(root);
            query.orderBy(cb.asc(root.get("timestamp")));
            return entityManager.createQuery(query).getResultList();
        } finally {
            entityManager.close();
        }
    }

}
