package com.starksoftware.library.security.model.exception;

public class UserNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Usuário não encontrado");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
