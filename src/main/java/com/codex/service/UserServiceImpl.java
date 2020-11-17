package com.codex.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.exception.UserNotFoundException;
import com.codex.model.User;
import com.codex.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	UserRepository userRepository;

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException(String.format("User for the id %d is not available", userId)));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(User user) {
		logger.info("User -> {}", user);
		return userRepository.save(user);
	}

	@Override
	public void update(User user, int userId) {
		userRepository.save(user);
	}

	@Override
	public void delete(int userId) {
		userRepository.deleteById(userId);
	}
}
