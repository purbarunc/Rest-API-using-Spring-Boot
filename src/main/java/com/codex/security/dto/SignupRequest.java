package com.codex.security.dto;

import lombok.Data;

@Data
public class SignupRequest {
	private String name;
	private String username;
	private String password;
}
