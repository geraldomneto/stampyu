package com.starksoftware.library.security.model.exception;

public class RoleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleException() {
		super("Usuário não possui o acesso necessário");
	}

	public RoleException(String message) {
		super(message);
	}
}
