package com.starksoftware.library.security.model.exception;

public class PasswordNotMatchException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNotMatchException() {
		super("Senhas não conferem");
	}

	public PasswordNotMatchException(String message) {
		super(message);
	}
}
