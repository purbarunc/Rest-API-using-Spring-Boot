package com.codex.security.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = -2884724941267355100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String authorityRole;

	@ManyToOne
	private User user;

	@Override
	public String getAuthority() {
		return this.authorityRole;
	}

	@Override
	public String toString() {
		return "Authority [authority=" + authorityRole + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAuthorityRole(String authority) {
		this.authorityRole = authority;
	}

}
