package com.apis.blog_app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.apis.blog_app.dto.PostDto;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.Post;
import com.apis.blog_app.model.User;
import com.apis.blog_app.utils.PostResponse;

public interface PostService {

	public void create(PostDto postDto, int user_id, int category_id);

	public void update(PostDto postDto, int post_id);

	public void delete(int post_id);
	
	public PostDto getPost(int post_id);
	
	public void create(MultipartFile image, int user_id)  throws IOException;
	
	public List<PostDto> getPostsByUser(int user_id);
	
	public List<PostDto> getPostsByCategory(int category_id);
	
	public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);
	

	
	public List<PostDto> searchPostByTitle(String keyword);
}
