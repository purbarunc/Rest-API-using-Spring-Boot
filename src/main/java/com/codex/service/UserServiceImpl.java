package com.codex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codex.exception.UserNotFoundException;
import com.codex.model.User;
import com.codex.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
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
