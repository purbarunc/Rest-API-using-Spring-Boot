package com.codex.exception;

public class StudentNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 2842697592513138469L;

	public StudentNotFoundException(String message) {
		super(message);
	}
}
