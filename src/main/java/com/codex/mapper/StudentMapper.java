package com.codex.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codex.dto.StudentRequest;
import com.codex.model.Student;

@Component
public class StudentMapper {
	@Autowired
    private ModelMapper modelMapper;

	public Student convertToEntity(StudentRequest studentRequest) {
		return modelMapper.map(studentRequest, Student.class);        
	}
}
