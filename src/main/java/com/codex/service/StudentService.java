package com.codex.service;

import java.util.List;

import com.codex.model.Student;

public interface StudentService {
	Student findById(int studentId);

	List<Student> findAll();

	Student create(Student student);

	void delete(int studentId);

	void update(Student student);
}
