package com.codex.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.dto.UserPostRequest;
import com.codex.dto.UserRequest;
import com.codex.exception.UserNotFoundException;
import com.codex.httpresponse.ErrorResponse;
import com.codex.httpresponse.HttpResponse;
import com.codex.httpresponse.SuccessResponse;
import com.codex.mapper.UserMapper;
import com.codex.mapper.UserPostMapper;
import com.codex.model.Post;
import com.codex.model.User;
import com.codex.service.PostService;
import com.codex.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserPostMapper userPostMapper;

	@GetMapping("/users")
	public ResponseEntity<List<User>> allUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<User> getUser(@RequestParam("id") int userId) {
		return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@NotNull @Valid @RequestBody UserRequest userRequest) {
		User user=mapUserDTOToEntity(userRequest);
		return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
	}

	@PutMapping("/user")
	public ResponseEntity<HttpResponse> updateUser(@NotNull @Valid @RequestBody UserRequest userRequest, @RequestParam("id") int userId) {
		userRequest.setId(userId);
		User user=mapUserDTOToEntity(userRequest);
		userService.update(user, userId);
		return new ResponseEntity<>(new SuccessResponse(String.format("User id=%d has been updated", userId)),
				HttpStatus.OK);
	}

	@DeleteMapping("/user")
	public ResponseEntity<HttpResponse> deleteUser(@RequestParam("id") int userId) {
		try {
			userService.delete(userId);
			return new ResponseEntity<>(new SuccessResponse(String.format("User id=%d succesfully deleted", userId)),
					HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			return new ResponseEntity<>(new ErrorResponse(String.format("User id=%d not found", userId)),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/user/posts")
	public ResponseEntity<List<Post>> allPosts(@RequestParam("id") int userId) {
		return new ResponseEntity<>(userService.findById(userId).getPosts(), HttpStatus.OK);
	}

	@PostMapping("/user/posts")
	public ResponseEntity<Post> createUserPost(@RequestParam("id") int userId, @NotNull @RequestBody UserPostRequest userPostRequest) {
		Post post=userPostMapper.convertToEntity(userPostRequest);
		User user = userService.findById(userId);
		if (user == null) {
			throw new UserNotFoundException("id: " + userId);
		}
		post.setUsers(user);
		return new ResponseEntity<>(postService.create(post), HttpStatus.CREATED);
	}
	
	private User mapUserDTOToEntity(UserRequest userRequest) {
		return userMapper.convertToEntity(userRequest);
	}
}
