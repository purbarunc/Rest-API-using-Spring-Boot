package com.codex.security.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("default")
class UserDetailsServiceImplTest {
	@Autowired(required = false)
	private BCryptPasswordEncoder encoder;

	@Test
	@Disabled
	void generateEncryptedPassword() {
		String rawPassword = "1234";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
		assertTrue(true);
	}
}
