package com.codex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codex.exception.StudentNotFoundException;
import com.codex.model.Student;
import com.codex.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	StudentRepository studentRepository;

	@Override
	public Student findById(int studentId) {
		return studentRepository.findById(studentId).orElseThrow(
				() -> new StudentNotFoundException(String.format("Student for the id %d is not available", studentId)));
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student create(Student student) {
		System.out.println(student);
		return studentRepository.save(student);
	}

	@Override
	public void update(Student student) {
		studentRepository.save(student);
	}

	@Override
	public void delete(int studentId) {
		studentRepository.deleteById(studentId);
	}
}
