package com.codex.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.codex.dto.UserPostRequest;
import com.codex.model.Post;

@Component
public class UserPostMapper {
	@Autowired
	private ModelMapper modelMapper;

	public Post convertToEntity(UserPostRequest userPostRequest) {
		return modelMapper.map(userPostRequest, Post.class);
	}
}
