package com.apis.blog_app.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.apis.blog_app.dto.CategoryDto;
import com.apis.blog_app.dto.PostDto;
import com.apis.blog_app.exception.ResourceAlreadyExists;
import com.apis.blog_app.exception.ResourceNotFound;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.Post;
import com.apis.blog_app.model.User;
import com.apis.blog_app.repository.CategoryRepository;
import com.apis.blog_app.repository.PostRepository;
import com.apis.blog_app.repository.UserRepository;
import com.apis.blog_app.utils.PostResponse;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	Post post;
	
	@Value("${image.file.path}")
	private String imageFilePath;

	@Override
	public void create(PostDto postDto, int user_id, int category_id) {
		User user = userRepository.findById(user_id).get();
		Category category  = categoryRepository.findById(category_id).get();

		Post post = dtoToEntity(postDto);
		post.setUser(user);
		post.setCategory(category);
	    post.setAddedDate(LocalDate.now());
		
		postRepository.save(post);
	}
	
	@Override
	public void update(PostDto postDto, int id) {
		postRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Post", String.valueOf(id)));
		Post post = dtoToEntity(postDto);
		post.setId(id);
		postRepository.save(post);
	}
	
	@Override
	public void delete(int id) {
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Post", String.valueOf(id)));
		postRepository.delete(post);
	}

	@Override
	public PostDto getPost(int post_id) {
		Post post = postRepository.findById(post_id).orElseThrow(()->new ResourceNotFound("Post", String.valueOf(post_id) ));
		return entityToDto(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber,int pageSize, String sortBy,  String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			 sort = Sort.by(sortBy).ascending();
		}else {
			 sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageable);
		
		List<PostDto> pagePosts=new ArrayList<>();
		page.getContent().forEach((post)-> {
			pagePosts.add(entityToDto(post));
		});
		return new PostResponse(pagePosts, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
		
	}
	
	@Override
	public void create(MultipartFile image, int id) throws IOException {
//		String imageName = image.getOriginalFilename();
//		String fullImagePath = imageFilePath +File.separator+imageName;
//		File file = new File(imageFilePath);
//		if(!file.exists()) file.mkdir();
//		try {
//			Files.copy(image.getInputStream(), Paths.get(fullImagePath));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		Post post = postRepository.findById(id).get();
		
	    post.setImageData(image.getBytes());
		post.setImageName(image.getOriginalFilename());
		postRepository.save(post);
		
	}

	

	@Override
	public List<PostDto> getPostsByUser(int user_id) {
		User user = userRepository.findById(user_id).get();
		List<Post> posts = postRepository.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((Post)->entityToDto(Post)).toList();
	 	return postDtos;
	}

	@Override
	public List<PostDto> getPostsByCategory(int category_id) {
		Category category = categoryRepository.findById(category_id).get();
		List<Post> posts = postRepository.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((Post)->entityToDto(Post)).toList();
	 	return postDtos;
	}

	@Override
	public List<PostDto> searchPostByTitle(String keyword) {
		return postRepository.findByTitleContaining(keyword).stream().map((Post)->entityToDto(Post)).toList();
	}
	
	public Post dtoToEntity(PostDto postDto) {
		return modelMapper.map(postDto, Post.class);
	}

	public PostDto entityToDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}
	
	

}
