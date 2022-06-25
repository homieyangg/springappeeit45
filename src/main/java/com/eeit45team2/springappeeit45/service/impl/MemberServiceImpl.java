package com.eeit45team2.springappeeit45.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeit45team2.springappeeit45.model.Member;
import com.eeit45team2.springappeeit45.repository.MemberRepository;
import com.eeit45team2.springappeeit45.service.MemberService;
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	MemberRepository memberDao;
	
	@Autowired
	public MemberServiceImpl(MemberRepository memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public void save(Member member) {
		memberDao.save(member);
	}

	@Override
	public void delete(Integer id) {
		memberDao.delete(id);
	}

	@Override
	public void update(Member member) {
		memberDao.update(member);
	}

	@Override
	public List<Member> getAllMembers() {
		return memberDao.getAll();
	}

	@Override
	public Member get(Integer id) {
		return memberDao.get(id);
		
	}

}
