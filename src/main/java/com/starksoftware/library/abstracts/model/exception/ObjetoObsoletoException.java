package com.starksoftware.library.abstracts.model.exception;

import com.starksoftware.library.abstracts.model.entity.IEntidade;

public class ObjetoObsoletoException extends RuntimeException {

	private static final long serialVersionUID = 2164259926047302608L;

	private IEntidade objetoObsoleto;

	public ObjetoObsoletoException(String message, IEntidade objeto) {
		super(message);
		this.objetoObsoleto = objeto;
	}

	public IEntidade getObjetoObsoleto() {
		return objetoObsoleto;
	}
}
