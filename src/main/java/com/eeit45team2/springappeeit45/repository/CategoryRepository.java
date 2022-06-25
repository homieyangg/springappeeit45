package com.eeit45team2.springappeeit45.repository;

import java.util.List;
import com.eeit45team2.springappeeit45.model.Category;

public interface CategoryRepository {
	List<Category> getAllCategories();
	Category getCategory(Integer id);
}
