package com.apis.blog_app.service;

import com.apis.blog_app.dto.CategoryDto;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.User;

public interface CategoryService {

	public void create(CategoryDto categoryDto);
	public void update(CategoryDto categoryDto);
	public CategoryDto get(String name);
	public CategoryDto delete(String name);
}
