
package com.apis.blog_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apis.blog_app.config.AppConstants;
import com.apis.blog_app.dto.CategoryDto;
import com.apis.blog_app.exception.ResourceAlreadyExists;
import com.apis.blog_app.exception.ResourceNotFound;
import com.apis.blog_app.model.Role;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.repository.RoleRepository;
import com.apis.blog_app.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void create(CategoryDto categoryDto) {
		
		categoryRepository.findByName(categoryDto.getName()).ifPresent((dto) -> {
			throw new ResourceAlreadyExists("Category", dto.getName());
		});
		categoryRepository.save(dtoToEntity(categoryDto));

	}
	
	@Override
	public void update(CategoryDto categoryDto) {
		categoryRepository.findByName(categoryDto.getName()).orElseThrow(() -> {
			throw new ResourceNotFound("Category", categoryDto.getName());
		});
		categoryRepository.save(dtoToEntity(categoryDto));

	}

	@Override
	public CategoryDto get(String categoryName) {
		Category category = categoryRepository.findByName(categoryName).orElseThrow(()->{
			throw new ResourceNotFound("Category", categoryName);
		}); 
		return entityToDto(category);
	}

	@Override
	public CategoryDto delete(String categoryName) {
		Category category = categoryRepository.findByName(categoryName).orElseThrow(()->{
			throw new ResourceNotFound("Category", categoryName);
		}); 
		categoryRepository.delete(category);
		return entityToDto(category);
	}
	
	public Category dtoToEntity(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}

	public CategoryDto entityToDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

}
