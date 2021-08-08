package com.codex.security.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codex.security.domain.User;
import com.codex.security.dto.SignupRequest;
import com.codex.security.mapper.UserMapper;
import com.codex.security.service.user.UserService;

@RestController
@Profile("!local")
public class SignupController {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@NotNull @Valid @RequestBody SignupRequest signupRequest) {
		User user = mapUserDTOToEntity(signupRequest);
		return new ResponseEntity<>("Signup successful with username: " + userService.save(user).getUsername(),
				HttpStatus.CREATED);
	}

	private User mapUserDTOToEntity(SignupRequest signupRequest) {
		return userMapper.convertToEntity(signupRequest);
	}
}
