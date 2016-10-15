package com.starksoftware.library.security.model.entity;

import java.security.Principal;

import com.starksoftware.library.security.model.enumeration.RoleAccess;

public class FrwPrincipal implements Principal {

	private String username;
	private RoleAccess role;

	@Override
	public String getName() {
		return username;
	}

	public void setName(String name) {
		username = name;
	}

	public RoleAccess getRole() {
		return role;
	}

	public void setRole(RoleAccess role) {
		this.role = role;
	}
}
