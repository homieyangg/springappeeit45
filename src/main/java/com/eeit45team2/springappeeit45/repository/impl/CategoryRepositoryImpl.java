package com.eeit45team2.springappeeit45.repository.impl;

import com.eeit45team2.springappeeit45.model.Category;
import com.eeit45team2.springappeeit45.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    @PersistenceContext
    EntityManager entityManager;

    public CategoryRepositoryImpl() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Category> getAllCategories() {
        String hql = "FROM Category";
//        Session session = factory.getCurrentSession();
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public Category getCategory(Integer id) {
//        Session session = factory.getCurrentSession();
        return entityManager.find(Category.class, id);
    }
}
