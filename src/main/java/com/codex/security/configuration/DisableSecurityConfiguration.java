package com.codex.security.configuration;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = "app.security.enable", havingValue = "disable")
@Configuration
@Order(1000)
public class DisableSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@PostConstruct
	void init() {
		log.info("Security disabled! All urls are permited");
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/**");
	}
}
