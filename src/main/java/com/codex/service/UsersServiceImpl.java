package com.codex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codex.exception.UserNotFoundException;
import com.codex.model.Users;
import com.codex.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	UsersRepository userRepository;

	@Override
	public Users findById(int userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException(String.format("User for the id %d is not available", userId)));
	}

	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Users create(Users user) {
		return userRepository.save(user);
	}

	@Override
	public void update(Users user, int userId) {
		userRepository.save(user);
	}

	@Override
	public void delete(int userId) {
		userRepository.deleteById(userId);
	}
}
