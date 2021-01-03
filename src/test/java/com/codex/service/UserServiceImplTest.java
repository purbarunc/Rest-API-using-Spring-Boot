package com.codex.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.codex.model.User;
import com.codex.repository.UserRepository;

@SpringBootTest
class UserServiceImplTest {
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@BeforeEach
	public void testSetup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void testFindById() {
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(getUser()));
		User actualUser=userServiceImpl.findById(1);
		assertEquals(getUser(), actualUser);
	}

	private User getUser() {
		User user = new User();
		user.setName("Ashish");
		user.setAge(35);
		user.setCity("Kota");
		return user;
	}

}
