package com.apis.blog_app.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apis.blog_app.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	public Optional<Category> findByName(String name);
	
}
