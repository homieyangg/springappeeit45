package com.eeit45team2.springappeeit45.service;

import com.eeit45team2.springappeeit45.model.Category;

import java.util.List;


public interface CategoryService {
	List<Category> getAllCategories();
	Category getCategory(Integer id);
}
