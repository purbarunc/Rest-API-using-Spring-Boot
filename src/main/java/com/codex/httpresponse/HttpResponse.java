package com.codex.httpresponse;

public abstract class HttpResponse {
	private String msg;

	protected HttpResponse() {
		super();
	}

	protected HttpResponse(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
