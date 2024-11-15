package com.apis.blog_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apis.blog_app.config.AppConstants;
import com.apis.blog_app.dto.UserDto;
import com.apis.blog_app.exception.ResourceAlreadyExists;
import com.apis.blog_app.exception.ResourceNotFound;
import com.apis.blog_app.model.Role;
import com.apis.blog_app.model.User;
import com.apis.blog_app.repository.RoleRepository;
import com.apis.blog_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public void create(UserDto userDto) {
		
		userRepository.findByEmail(userDto.getEmail()).ifPresent((dto) -> {
			throw new ResourceAlreadyExists("User", dto.getEmail());
		});
		User user = dtoToEntity(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role defaultRole = roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(defaultRole);
		userRepository.save(user);

	}
	
	@Override
	public void update(UserDto userDto) {
		User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(()->{
			throw new ResourceNotFound("User", userDto.getEmail());
		});
		User userToBeSet = dtoToEntity(userDto);
		userToBeSet.setId(user.getId());
		userRepository.save(userToBeSet);

	}

	@Override
	public UserDto get(String username) {
		User user = userRepository.findByEmail(username).orElseThrow(()->{
			throw new ResourceNotFound("User", username);
		}); 
		return entityToDto(user);
	}

	@Override
	public UserDto delete(String username) {
		User user = userRepository.findByEmail(username).orElseThrow(()->{
			throw new ResourceNotFound("User", username);
		}); 
		userRepository.delete(user);
		return entityToDto(user); 
	}
	
	public User dtoToEntity(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

	public UserDto entityToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

}
