package com.codex.utility;

import lombok.Getter;

public enum PROFILE {
	LOCAL("local"), INT("int");

	@Getter
	private String value;

	PROFILE(String value) {
		this.value = value;
	}
}
