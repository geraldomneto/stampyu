package com.starksoftware.library.abstracts.rest;

import javax.ws.rs.core.Response.ResponseBuilder;

import com.starksoftware.library.abstracts.dto.IDTO;
import com.starksoftware.library.abstracts.model.business.AbstractFacade;

/**
 * 
 * Classe responsavel por abstrair as services utilizadas no Rest, utilizando-se
 * de tipificacao.
 *
 * @author miqueias.gomes
 * @since 09/08/2016
 * 
 * @param <S>
 * @param <D>
 * 
 */
public abstract class AbstractRest<S extends AbstractFacade, D extends IDTO> extends AbstractResponse {

	protected D dto;
	private S service;

	protected S getService() {
		return service;
	}

	protected void setService(final S service) {
		this.service = service;
	}

	public D getDto() {
		return dto;
	}

	public void setDto(final D dto) {
		this.dto = dto;
	}

	protected ResponseBuilder getBuilder() {
		return builder;
	}

	protected void setBuilder(final ResponseBuilder builder) {
		this.builder = builder;
	}
}
