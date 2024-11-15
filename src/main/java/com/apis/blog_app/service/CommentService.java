package com.apis.blog_app.service;

import com.apis.blog_app.dto.CommentDto;
import com.apis.blog_app.model.Category;
import com.apis.blog_app.model.Comment;
import com.apis.blog_app.model.User;

public interface CommentService {

	public void create(CommentDto commentDto, int user_id, int post_id);
	public void update(CommentDto commentDto, int comment_id);
	public void delete(int id);
}
