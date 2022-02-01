package com.codex.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.codex.model.Student;
import com.codex.repository.StudentRepository;

@SpringBootTest
@ActiveProfiles("local")
class StudentServiceImplTest {
	@InjectMocks
	private StudentServiceImpl studentServiceImpl;

	@Mock
	private StudentRepository studentRepository;

	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("findById is successful")
	void findById() {
		when(studentRepository.findById(anyInt())).thenReturn(Optional.of(getStudent()));
		assertEquals(getStudent(), studentServiceImpl.findById(1));
		verify(studentRepository,times(1)).findById(anyInt());
	}
	
	@Test
	@DisplayName("findAll is successful")
	void findAll() {
		List<Student> mockList = new ArrayList<>();
		mockList.add(mock(Student.class));
		when(studentRepository.findAll()).thenReturn(mockList);
		studentServiceImpl.findAll();
		verify(studentRepository,times(1)).findAll();
	}
	
	@Test
	@DisplayName("create is successful")
	void create() {	
		when(studentRepository.save(any())).thenReturn(getStudent());
		studentServiceImpl.create(mock(Student.class));
		verify(studentRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("update is successful")
	void update() {	
		studentServiceImpl.update(mock(Student.class));
		verify(studentRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("delete is successful")
	void delete() {	
		studentServiceImpl.delete(1);
		verify(studentRepository,times(1)).deleteById(anyInt());
	}
	private Student getStudent() {
		return Student.builder().name("Ashish").age(35).city("Kota").build();
	}
}
