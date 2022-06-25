package com.eeit45team2.springappeeit45.repository;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.eeit45team2.springappeeit45.model.Member;

@Repository
public interface MemberRepository {
	void save(Member member);
	void delete(Integer id);
	Member get(Integer id);
	void update(Member member);
	List<Member> getAll();
}
