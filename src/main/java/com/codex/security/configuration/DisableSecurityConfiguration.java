package com.codex.security.configuration;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = "app.security.enable", havingValue = "disable")
@Configuration
@Order(1000)
public class DisableSecurityConfiguration {
	@PostConstruct
	void init() {
		log.info("Security disabled! All urls are permited");
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**");
    }
}
