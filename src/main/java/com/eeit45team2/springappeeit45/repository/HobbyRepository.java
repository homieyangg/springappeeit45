package com.eeit45team2.springappeeit45.repository;

import java.util.List;
import com.eeit45team2.springappeeit45.model.Hobby;

public interface HobbyRepository {
	List<Hobby> getAllHobbies();
	Hobby getHobby(Integer id);
}
