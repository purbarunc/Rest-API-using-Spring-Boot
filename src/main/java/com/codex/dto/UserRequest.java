package com.codex.dto;

import java.util.List;

import com.codex.model.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private int id;
	private String name;
	private int age;
	private String city;
	List<Post> posts;
}
