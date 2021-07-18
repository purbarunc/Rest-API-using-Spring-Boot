package com.codex.security.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codex.security.domain.User;
import com.codex.security.repository.UserRepository;
import com.codex.security.util.CustomSecuredUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		log.info("User Logged In--->{}",user);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username and password");
		}
		return new CustomSecuredUser(user);
	}

}
