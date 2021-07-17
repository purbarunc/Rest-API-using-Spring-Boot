package com.codex.service;

import java.util.List;

import com.codex.model.Users;


public interface UsersService {
	Users findById(int userId);

	List<Users> findAll();

	Users create(Users user);

	void delete(int userId);

	void update(Users user, int userId);
}
