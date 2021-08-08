package com.codex.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.codex.dto.StudentPostRequest;
import com.codex.model.Post;

@Component
public class StudentPostMapper {
	@Autowired
	private ModelMapper modelMapper;

	public Post convertToEntity(StudentPostRequest studentPostRequest) {
		return modelMapper.map(studentPostRequest, Post.class);
	}
}
