package com.apis.blog_app.service;

import com.apis.blog_app.dto.UserDto;
import com.apis.blog_app.model.User;

public interface UserService {

	public void create(UserDto userDto);
	public void update(UserDto userDto);
	public UserDto get(String username);
	public UserDto delete(String username);
}
