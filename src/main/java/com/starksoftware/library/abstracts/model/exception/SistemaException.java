package com.starksoftware.library.abstracts.model.exception;

import javax.ejb.ApplicationException;

/**
 * 
 * @author framework
 *
 */
@ApplicationException(rollback = true)
public class SistemaException extends Exception {

	private static final long serialVersionUID = 557531852230728423L;

	protected Object[] messageParameters;

	public SistemaException(String message) {
		super(message);
	}

	public SistemaException(String message, Object... parameters) {
		super(message);
		this.messageParameters = parameters;
	}

	public SistemaException(String message, Throwable t) {
		super(message, t);
	}

	public Object[] getMessageParameters() {
		return messageParameters;
	}
}
