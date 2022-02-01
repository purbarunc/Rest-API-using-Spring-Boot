package com.codex.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
	@NotNull
	private String name;
	@NotNull
	private int age;
	@NotNull
	private String city;
}
