package com.eeit45team2.springappeeit45.repository.impl;

import com.eeit45team2.springappeeit45.model.Category;
import com.eeit45team2.springappeeit45.model.Hobby;
import com.eeit45team2.springappeeit45.model.Member;
import com.eeit45team2.springappeeit45.repository.CategoryRepository;
import com.eeit45team2.springappeeit45.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    EntityManager entityManager;


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    HobbyRepositoryimpl hobbyRepository;

    public MemberRepositoryImpl() {
    }

    @Override
    public void save(Member member) {
//		Session session = getSession();
        Category c = categoryRepository.getCategory(member.getCategory().getId());
        member.setCategory(c);
        Hobby h = hobbyRepository.getHobby(member.getHobby().getId());
        member.setHobby(h);
        entityManager.persist(member);
    }

    @Override
    public void delete(Integer id) {
//		Session session = getSession();
        Member member = get(id);
        if (member != null) {
            member.setCategory(null);
            member.setHobby(null);
            entityManager.remove(member);
        }
    }

    @Override
    public void update(Member member) {
        if (member != null && member.getId() != null) {
//			Session session = getSession();
            entityManager.merge(member);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Member> getAll() {
        String hql = "FROM Member";
//		Session session = getSession();
        List<Member> list = entityManager.createQuery(hql).getResultList();
        return list;
    }


    @Override
    public Member get(Integer id) {
        return entityManager.find(Member.class, id);
    }
}
