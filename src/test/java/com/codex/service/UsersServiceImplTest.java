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
import com.codex.model.Users;
import com.codex.repository.UsersRepository;

@SpringBootTest
class UsersServiceImplTest {
	@InjectMocks
	private UsersServiceImpl userServiceImpl;

	@Mock
	private UsersRepository userRepository;

	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("findById is successful")
	void findById() {
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(getUser()));
		assertEquals(getUser(), userServiceImpl.findById(1));
		verify(userRepository,times(1)).findById(anyInt());
	}
	
	@Test
	@DisplayName("findAll is successful")
	void findAll() {
		List<Users> mockList = new ArrayList<>();
		mockList.add(mock(Users.class));
		when(userRepository.findAll()).thenReturn(mockList);
		userServiceImpl.findAll();
		verify(userRepository,times(1)).findAll();
	}
	
	@Test
	@DisplayName("create is successful")
	void create() {	
		when(userRepository.save(any())).thenReturn(getUser());
		userServiceImpl.create(mock(Users.class));
		verify(userRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("update is successful")
	void update() {	
		userServiceImpl.update(mock(Users.class),1);
		verify(userRepository,times(1)).save(any());
	}
	
	@Test
	@DisplayName("delete is successful")
	void delete() {	
		userServiceImpl.delete(1);
		verify(userRepository,times(1)).deleteById(anyInt());
	}
	private Users getUser() {
		return Users.builder().name("Ashish").age(35).city("Kota").build();
	}
}
