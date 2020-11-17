package com.codex.exception;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 2842697592513138469L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
