package com.starksoftware.library.abstracts.rest;

import java.util.logging.Logger;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.starksoftware.library.abstracts.dto.ResponseFrwDTO;
import com.starksoftware.library.abstracts.model.enumeration.ResponseCodesEnum;

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
public abstract class AbstractResponse {

	protected Response.ResponseBuilder builder;
	private static final String ERROR = "error";
	private static final String SUCCESS = "success";
	private ResponseFrwDTO responseDTO = new ResponseFrwDTO();
	public static final int DUZENTOS = 200;

	protected Response ok(Object data) {
		return success(DUZENTOS, data);
	}

	/**
	 * 
	 * Metodo utilizado para devolver algum cookie para o browser.
	 * 
	 * @param data
	 * @param cookie
	 * @return
	 */
	protected Response ok(Object data, NewCookie cookie) {
		return successCookie(data, cookie);
	}

	protected Response created(Object data) {
		return success(201, data);
	}

	protected Response successWithoutBody() {
		return Response.noContent().build();
	}

	protected Response error(Object data) {
		return error(ResponseCodesEnum.BAD_REQUEST, 400, data);
	}

	protected Response errorBadRequest() {
		return error(ResponseCodesEnum.BAD_REQUEST, 400, null);
	}

	/**
	 * 
	 * Not found
	 * 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	protected Response errorNotFound(ResponseCodesEnum errorCode, Object data) {
		return error(errorCode, 404, data);
	}

	/**
	 * 
	 * Quando autenticação é necessária.
	 * 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	protected Response errorUnauthorized(Object data) {
		return errorUnauthorized(ResponseCodesEnum.UNAUTHORIZED, data);
	}

	protected Response errorUnauthorized(ResponseCodesEnum errorCode, Object data) {
		return error(errorCode, 401, data);
	}

	/**
	 * 
	 * Proibido mesmo com autenticação
	 * 
	 * @param errorCode
	 * @param data
	 * @return
	 */
	protected Response errorForbidden(ResponseCodesEnum errorCode, Object data) {
		return error(errorCode, 403, data);
	}

	protected Response errorForbidden(Object data) {
		return this.errorForbidden(ResponseCodesEnum.FORBIDDEN, data);
	}
	
	protected Response internalError(Object data) {
		return this.internalError(ResponseCodesEnum.INTERNAL_SERVER_ERROR, data);
	}

	private Response internalError(ResponseCodesEnum errorCode, Object data) {
		return error(errorCode, 500, data);
	}

	protected Response success(Integer codeHeader, Object data) {

		responseDTO.setCode(codeHeader);
		responseDTO.setStatus(SUCCESS);
		responseDTO.setData(data);
		return Response.ok(responseDTO, MediaType.APPLICATION_JSON).status(DUZENTOS).build();
	}

	/**
	 * Metodo responsavel por obter o response builder com status de uma
	 * requisicao (bad request).
	 *
	 * @author miqueias.gomes
	 * @param log
	 * @param ex
	 * @return response builder com status de uma requisicao.
	 */
	protected Response.ResponseBuilder tratarExcecaoRest(final Logger log, final Exception ex) {
		String message = ex.getMessage();
		if (ex.getCause() instanceof ValidationException) {
			message = ex.getCause().getMessage();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(message);
	}

	private Response error(ResponseCodesEnum errorCode, Integer codeHeader, Object data) {

		responseDTO.setCode(errorCode.getId());
		responseDTO.setStatus(ERROR);
		if(data != null)
			responseDTO.setMessage(data.toString());
		return Response.ok(responseDTO, MediaType.APPLICATION_JSON).status(codeHeader).build();
	}

	/**
	 * 
	 * Metodo utilizado para devolver algum cookie para o browser.
	 * 
	 * @param codeHeader
	 * @param data
	 * @param cookie
	 * @return
	 */
	private Response successCookie(final Object data, final NewCookie cookie) {

		responseDTO.setCode(DUZENTOS);
		responseDTO.setStatus(SUCCESS);
		responseDTO.setData(data);

		return Response.ok(responseDTO, MediaType.APPLICATION_JSON).status(DUZENTOS).cookie(cookie).build();
	}

	protected ResponseBuilder getBuilder() {
		return builder;
	}

	protected void setBuilder(final ResponseBuilder builder) {
		this.builder = builder;
	}
}
