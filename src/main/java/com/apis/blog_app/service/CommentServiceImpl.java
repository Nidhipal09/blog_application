package com.apis.blog_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apis.blog_app.dto.CommentDto;
import com.apis.blog_app.dto.PostDto;
import com.apis.blog_app.exception.ResourceAlreadyExists;
import com.apis.blog_app.exception.ResourceNotFound;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.Comment;
import com.apis.blog_app.model.Post;
import com.apis.blog_app.model.User;
import com.apis.blog_app.repository.CategoryRepository;
import com.apis.blog_app.repository.CommentRepository;
import com.apis.blog_app.repository.PostRepository;
import com.apis.blog_app.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void create(CommentDto commentDto, int user_id, int post_id) {
		User user = userRepository.findById(user_id).get();
		Post post = postRepository.findById(post_id).get();
 
		Comment comment = dtoToEntity(commentDto);
		comment.setUser(user);
		comment.setPost(post);
		
		commentRepository.save(comment);
	}
	
	@Override
	public void update(CommentDto commentDto, int comment_id) {
		Comment comment_found = commentRepository.findById(comment_id).get();
 
		comment_found.setContent(commentDto.getContent());
		
		commentRepository.save(comment_found);
	}

	@Override
	public void delete(int comment_id) {
		Comment comment= commentRepository.findById(comment_id).get();
		
		commentRepository.delete(comment);
	}
	
	public Comment dtoToEntity(CommentDto commentDto) {
		return modelMapper.map(commentDto, Comment.class);
	}

	public CommentDto entityToDto(Comment comment) {
		return modelMapper.map(comment, CommentDto.class);
	}

}
