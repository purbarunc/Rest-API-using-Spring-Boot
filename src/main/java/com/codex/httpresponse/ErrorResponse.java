package com.codex.httpresponse;

public class ErrorResponse extends HttpResponse{

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String msg) {
		super(msg);
	}
}
