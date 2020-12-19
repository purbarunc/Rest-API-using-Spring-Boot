package com.codex.dto;

import com.codex.model.User;

public class UserPostRequest {
	private int id;
	private String userPosts;
	private User users;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserPosts() {
		return userPosts;
	}
	public void setUserPosts(String userPosts) {
		this.userPosts = userPosts;
	}
	public User getUsers() {
		return users;
	}
	public void setUsers(User users) {
		this.users = users;
	}
}
