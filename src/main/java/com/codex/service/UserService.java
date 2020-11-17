package com.codex.service;

import java.util.List;

import com.codex.model.User;

public interface UserService {
	User findById(int userId);

	List<User> findAll();

	User create(User user);

	void delete(int userId);

	void update(User user, int userId);
}
