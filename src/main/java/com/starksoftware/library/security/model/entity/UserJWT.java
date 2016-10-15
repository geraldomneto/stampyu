package com.starksoftware.library.security.model.entity;

import com.starksoftware.library.security.model.enumeration.RoleAccess;

public class UserJWT {

	private String username;
	private RoleAccess role;

	public UserJWT(String username, RoleAccess role) {
		this.username = username;
		this.role = role;
	}

	public RoleAccess getRole() {
		return role;
	}

	public void setRole(RoleAccess role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
