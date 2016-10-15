package com.starksoftware.stampyu.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.model.business.TokenHandler;
import com.starksoftware.library.security.model.entity.Usuario;
import com.starksoftware.library.security.util.CommonSecurity;
import com.starksoftware.stampyu.dto.UsuarioDTO;
import com.starksoftware.stampyu.model.business.LoginFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Login")
@Path("/login")
public class LoginWS extends AbstractResponse {

	private static final Logger LOG = Logger.getLogger(LoginWS.class.getName());
	private static final String MSG_ERRO = "MSG Erro: %s";
	private static final String ROOT_PATH = "/";

	@Inject
	private LoginFacade loginFacade;
	
	@Inject
	private TokenHandler tokenHandler;

	@Context
	private UriInfo uriInfo;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Serviço de login", notes = "Informar usuário e senha para autenticação")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response login(@ApiParam(value = "JSON", required = true) UsuarioDTO usuarioDTO) {

		try {
			Usuario usuario = loginFacade.buscaUsuario(usuarioDTO);
			if (usuario != null) {
				final String token = tokenHandler.createTokenForUser(usuario.getLogin(), usuario.getRoleAccess());
				return this.ok(usuarioDTO, obterCookieComNovoToken(token));
			}
			return errorUnauthorized(usuarioDTO);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			return this.internalError(null);
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastro de usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastraUsuario(@ApiParam(value = "JSON", required = true) UsuarioDTO usuarioDTO) {

		try {
			Usuario usuario = loginFacade.cadastraUsuario(usuarioDTO);
			final String token = tokenHandler.createTokenForUser(usuario.getLogin(), usuario.getRoleAccess());
			return this.ok(usuarioDTO, obterCookieComNovoToken(token));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
			return this.internalError(null);
		}
	}

	/**
	 * 
	 * Cria cookie valido.
	 * 
	 * @param token
	 * @return
	 */
	private NewCookie obterCookieComNovoToken(final String token) {

		return new NewCookie(CommonSecurity.SESSION_ID, token, ROOT_PATH, uriInfo.getBaseUri().getHost(), "",
				NewCookie.DEFAULT_MAX_AGE, false);
	}

}
