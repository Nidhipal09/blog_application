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

import com.apis.blog_app.dto.CommentDto;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.Comment;
import com.apis.blog_app.model.User;
import com.apis.blog_app.service.CategoryService;
import com.apis.blog_app.service.CommentService;
import com.apis.blog_app.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Comment APIs")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@PostMapping("/users/{user_id}/posts/{post_id}/comments")
	public void create(@Valid @RequestBody CommentDto commentDto, @RequestParam int user_id, int post_id) {
		commentService.create(commentDto, user_id, post_id);
	}
	
	@PutMapping("/comments/{comment_id}")
	public void update(@Valid @RequestBody CommentDto commentDto, @RequestParam int comment_id) {
		commentService.update(commentDto, comment_id);
	}
	
	@DeleteMapping("/comments/{comment_id}")
	public void delete(@Valid @RequestParam int comment_id) {
		commentService.delete(comment_id);
	}
}
