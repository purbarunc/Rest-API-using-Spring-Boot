package com.codex.controller.exceptionhandler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ExceptionResponse {
	@Getter
	private Date timeStamp;
	
	@Getter
	private String message;
	
	@Getter
	private String details;
}
