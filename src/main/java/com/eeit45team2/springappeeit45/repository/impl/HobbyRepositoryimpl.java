package com.eeit45team2.springappeeit45.repository.impl;

import com.eeit45team2.springappeeit45.model.Hobby;
import com.eeit45team2.springappeeit45.repository.HobbyRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class HobbyRepositoryimpl implements HobbyRepository {

    @PersistenceContext
    EntityManager entityManager;

    public HobbyRepositoryimpl() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Hobby> getAllHobbies() {
        String hql = "FROM Hobby";
//        Session session = getSession();
        return entityManager.createQuery(hql).getResultList();
    }


    @Override
    public Hobby getHobby(Integer id) {
//        Session session = getSession();
        return entityManager.find(Hobby.class, id);
    }
}
