package com.apis.blog_app.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apis.blog_app.config.AppConstants;
import com.apis.blog_app.dto.PostDto;
import com.apis.blog_app.model.Post;
import com.apis.blog_app.repository.PostRepository;
import com.apis.blog_app.service.PostService;
import com.apis.blog_app.utils.PostResponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "Post APIs")
public class PostController {

	@Autowired
	PostService postService;

	@PostMapping("/users/{user_id}/categories/{category_id}/posts")
	public void create(@RequestBody PostDto postDto, @PathVariable int user_id, @PathVariable int category_id) {
		postService.create(postDto, user_id, category_id);
	}
	
	@PutMapping("/posts/{post_id}")
	public void updatePost(@RequestBody PostDto postDto, @PathVariable int post_id) {
		postService.update(postDto, post_id);

	}
	
	@DeleteMapping("/posts/{post_id}")
	public void deletePost(@PathVariable int post_id) {
		postService.delete(post_id);

	}

//	@GetMapping("/posts/{post_id}")
//	public PostDto getPost(@PathVariable int post_id) {
//		return postService.getPost(post_id);
//
//	}

	@GetMapping("/posts")
	public PostResponse getAllPosts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.pageNumber, required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.pageSize, required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.sortBy, required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = AppConstants.sortDir, required = false) String sortDir) {
		// pageNumber starts from 0
		return postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

	}



	@GetMapping("/users/{user_id}/posts")
	public List<PostDto> getPostsByUser(@PathVariable int user_id) {
		return postService.getPostsByUser(user_id);
	}

	@GetMapping("/categories/{category_id}/posts")
	public List<PostDto> getPostsByCategory(@PathVariable int category_id) {
		return postService.getPostsByCategory(category_id);
	}

	@GetMapping("/posts/search/{keyword}")
	public List<PostDto> searchPostByTitle(@PathVariable String keyword) {
		return postService.searchPostByTitle(keyword);
	}
	
	@PostMapping(value = "/posts/{id}/image/upload")
	public void imageUpload(@RequestBody MultipartFile image, @PathVariable int id) {
		try {
			postService.create(image, id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value =  "/posts/image", produces = MediaType.IMAGE_PNG_VALUE)
	public void imageDownload(HttpServletResponse response) {
		try {
			InputStream is = new FileInputStream("images"+File.separator+"Screenshot (865).png");
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			StreamUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(value= "/posts/{post_id}", produces = MediaType.IMAGE_PNG_VALUE)
	public void getPost(@PathVariable int post_id, HttpServletResponse response) {
		PostDto postDto = postService.getPost(post_id);
		InputStream inputStream = new ByteArrayInputStream(postDto.getImageData());
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		try {
			StreamUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
