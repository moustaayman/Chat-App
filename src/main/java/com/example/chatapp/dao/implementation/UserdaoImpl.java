package com.example.chatapp.dao.implementation;

import com.example.chatapp.dao.Userdao;
import com.example.chatapp.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UserdaoImpl implements Userdao {
    private final EntityManagerFactory entityManagerFactory;

    public UserdaoImpl() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
    }
    @Override
    public void saveUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void updateUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(user));
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public User findUserById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(User.class, id);
        } finally {
            entityManager.close();
        }
    }
    @Override
    public User findUserByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            // Use native SQL to create a query to fetch the user by email
            String sql = "SELECT * FROM User u WHERE u.email = ?";
            Query query = entityManager.createNativeQuery(sql, User.class);
            query.setParameter(1, email);

            // Execute the query and get the result
            List<User> resultList = query.getResultList();

            // Check if a user was found
            return resultList.isEmpty() ? null : resultList.get(0);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<User> findAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root);
            return entityManager.createQuery(query).getResultList();
        } finally {
            entityManager.close();
        }
    }
}
