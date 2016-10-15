package com.starksoftware.library.security.model.exception;

public class BlockedUserAccessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BlockedUserAccessException() {
		super("Conta do usuário está bloqueada");
	}

	public BlockedUserAccessException(String message) {
		super(message);
	}
}
