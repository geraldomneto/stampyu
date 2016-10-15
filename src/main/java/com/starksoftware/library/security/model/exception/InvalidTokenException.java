package com.starksoftware.library.security.model.exception;

public class InvalidTokenException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super("Token inválido ou expirado");
	}

	public InvalidTokenException(String message) {
		super(message);
	}
}
