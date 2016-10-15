package com.starksoftware.library.abstracts.model.exception;

/**
 *
 * @author Marcelo Alves
 */
@SuppressWarnings("rawtypes")
public class ForeignKeyViolationException extends RuntimeException {

	private static final long serialVersionUID = -686346502892129869L;

	private Class entityClass;

	public ForeignKeyViolationException(String message, Class entityClass) {
		super(message);
		this.entityClass = entityClass;
	}

	public Class getEntityClass() {
		return entityClass;
	}
}
