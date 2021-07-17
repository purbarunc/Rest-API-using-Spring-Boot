package com.codex.dto;

import com.codex.model.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequest {
	private int id;
	private String userPosts;
	private Users users;
}
