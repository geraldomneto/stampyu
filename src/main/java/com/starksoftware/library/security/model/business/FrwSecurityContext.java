package com.starksoftware.library.security.model.business;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.SecurityContext;

import com.starksoftware.library.security.model.entity.FrwPrincipal;
import com.starksoftware.library.security.model.enumeration.RoleAccess;

@RequestScoped
public class FrwSecurityContext implements SecurityContext {

	private FrwPrincipal frwPrincipal = null;
	boolean secured;

	public FrwSecurityContext() {
		super();
		secured = false;
		frwPrincipal = new FrwPrincipal();
	}

	@Override
	public Principal getUserPrincipal() {
		return frwPrincipal;
	}

	public FrwPrincipal getUserFrwPrincipal() {
		return frwPrincipal;
	}

	public String getCurrentUsername() {
		return frwPrincipal.getName();
	}

	public RoleAccess getCurrentUserRole() {
		return frwPrincipal.getRole();
	}

	@Override
	public boolean isUserInRole(String role) {
		RoleAccess roleEnum = RoleAccess.USER;

		if ("admin".equals(role)) {
			roleEnum = RoleAccess.ADMIN;
		}

		return frwPrincipal.getRole().equals(roleEnum);
	}

	@Override
	public boolean isSecure() {
		return secured;
	}

	public void setSecure(boolean isSecure) {
		secured = isSecure;
	}

	@Override
	public String getAuthenticationScheme() {
		return null;
	}
}
