package com.eeit45team2.springappeeit45.service;

import com.eeit45team2.springappeeit45.model.Hobby;

import java.util.List;

public interface HobbyService {
	List<Hobby> getAllHobbies();
	Hobby getHobby(Integer id);
}

