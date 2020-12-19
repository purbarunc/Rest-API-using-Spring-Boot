package com.codex.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.dto.UserRequest;
import com.codex.model.User;

@Component
public class UserMapper {
	@Autowired
    private ModelMapper modelMapper;

	public User convertToEntity(UserRequest userInfoRequest) {
		return modelMapper.map(userInfoRequest, User.class);
        
	}
}
