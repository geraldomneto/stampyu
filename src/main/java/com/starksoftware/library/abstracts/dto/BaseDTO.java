package com.starksoftware.library.abstracts.dto;

import java.io.Serializable;

public class BaseDTO implements IDTO, Serializable {

	private static final long serialVersionUID = -7518440731517793974L;

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
