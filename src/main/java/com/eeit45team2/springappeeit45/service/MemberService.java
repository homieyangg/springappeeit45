package com.eeit45team2.springappeeit45.service;

import java.util.List;

import com.eeit45team2.springappeeit45.model.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberService {
	void save(Member member);
	void delete(Integer id);
	Member get(Integer id);
	void update(Member member);
	List<Member> getAllMembers();
	
}
