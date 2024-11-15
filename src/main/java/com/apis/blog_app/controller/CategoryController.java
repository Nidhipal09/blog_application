package com.apis.blog_app.controller;

import java.util.Map;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apis.blog_app.dto.CategoryDto;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.User;
import com.apis.blog_app.service.CategoryService;
import com.apis.blog_app.service.UserService;
import com.apis.blog_app.utils.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category APIs")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody CategoryDto categoryDto) {
		categoryService.create(categoryDto);
		return new ResponseEntity<>(new ApiResponse("Category created successfully", categoryDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody CategoryDto categoryDto) {
		categoryService.update(categoryDto);
		return new ResponseEntity<>(new ApiResponse("Category updated successfully", categoryDto), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<ApiResponse> delete(@Valid @RequestParam String name) {
		CategoryDto categoryDto = categoryService.delete(name);
		return new ResponseEntity<>(new ApiResponse("Category delete successfully", categoryDto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> get(@Valid @RequestParam String name) {
		CategoryDto categoryDto = categoryService.get(name);
		return new ResponseEntity<>(new ApiResponse("Category details fetched successfully", categoryDto), HttpStatus.ACCEPTED);
	}
}
