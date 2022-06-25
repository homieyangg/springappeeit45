package com.eeit45team2.springappeeit45.service.impl;

import javax.transaction.Transactional;

import com.eeit45team2.springappeeit45.repository.HobbyRepository;
import com.eeit45team2.springappeeit45.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eeit45team2.springappeeit45.model.Hobby;
import java.util.List;
@Service
@Transactional
public class HobbyServiceImpl implements HobbyService {

	HobbyRepository hobbyDao;
	
	@Autowired
	public HobbyServiceImpl(HobbyRepository hobbyDao) {
		this.hobbyDao = hobbyDao;
	}

	@Override
	public List<Hobby> getAllHobbies() {
		return hobbyDao.getAllHobbies();
	}

	@Override
	public Hobby getHobby(Integer id) {
		return hobbyDao.getHobby(id);
	}

}
