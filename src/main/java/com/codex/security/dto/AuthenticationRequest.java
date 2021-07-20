package com.codex.security.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest implements Serializable{
	private static final long serialVersionUID = -3300597397791031879L;
	private String username;
	private String password;
}
