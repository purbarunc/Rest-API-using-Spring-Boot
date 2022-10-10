package com.codex.utility;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringProfile {
	@Getter
	private String activeProfile;
	@Autowired
	private Environment environment;

	@PostConstruct
	void init() {
		String[] activeProfileArray = environment.getActiveProfiles();
		if (activeProfileArray == null || activeProfileArray.length == 0) {
			log.warn("spring.profiles.active is not set");
			this.activeProfile = "";
		} else if (activeProfileArray.length > 1) {
			throw new IllegalArgumentException("multiple spring.profiles.active is set");
		} else {
			this.activeProfile = activeProfileArray[0];
			log.info("Active spring profile: {}", this.activeProfile);
		}
	}

	public boolean isActiveProfile(PROFILE profile) {
		return this.activeProfile.equals(profile.getValue());
	}
}
