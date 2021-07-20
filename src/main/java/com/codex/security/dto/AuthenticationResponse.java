package com.codex.security.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthenticationResponse implements Serializable{
	private static final long serialVersionUID = 4078960922226325150L;
	@Getter
	private final String jwt;
}
