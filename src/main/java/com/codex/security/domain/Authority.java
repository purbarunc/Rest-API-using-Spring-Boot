package com.codex.security.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Data
public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = -1143904092089141445L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String authority;

	@ManyToOne
	private User user;

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
