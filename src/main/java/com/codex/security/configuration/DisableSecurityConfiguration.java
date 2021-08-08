package com.codex.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile("local")
@Configuration
@Order(1000)
public class DisableSecurityConfiguration extends WebSecurityConfigurerAdapter{
	@Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/**");
    }
}
