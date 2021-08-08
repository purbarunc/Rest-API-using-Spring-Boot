package com.codex.security.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.security.domain.User;
import com.codex.security.dto.SignupRequest;

@Component
public class UserMapper {
	@Autowired
    private ModelMapper modelMapper;

	public User convertToEntity(SignupRequest signupRequest) {
		return modelMapper.map(signupRequest, User.class);        
	}
}
