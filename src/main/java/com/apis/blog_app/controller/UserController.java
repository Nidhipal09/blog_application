package com.apis.blog_app.controller;

import java.util.Map;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.apis.blog_app.dto.UserDto;
import com.apis.blog_app.model.User;
import com.apis.blog_app.service.UserService;
import com.apis.blog_app.utils.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "User APIs")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> create(@Valid @RequestBody UserDto userDto) {
		userService.create(userDto);
		return new ResponseEntity<>(new ApiResponse("User created successfully", userDto), HttpStatus.CREATED);
	}

	@PutMapping("/")
	public ResponseEntity<ApiResponse> update(@Valid @RequestBody UserDto userDto) {
		userService.update(userDto);
		return new ResponseEntity<>(new ApiResponse("User updated successfully", userDto), HttpStatus.ACCEPTED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/")
	public ResponseEntity<ApiResponse> delete(@Valid @RequestParam String username) {
		UserDto userDto = userService.delete(username);
		return new ResponseEntity<>(new ApiResponse("User deleted successfully", userDto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> get(@Valid @RequestParam String username) {
		UserDto userDto = userService.get(username);
		return new ResponseEntity<>(new ApiResponse("User details fetched successfully", userDto), HttpStatus.FOUND);
	}
}
