//package com.starksoftware.library.security.rest;
//
//import java.util.Calendar;
//import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.inject.Inject;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.CookieParam;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.NewCookie;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.UriInfo;
//
//import org.apache.commons.lang.time.DateUtils;
//
//import com.starksoftware.library.abstracts.model.exception.SistemaException;
//import com.starksoftware.library.abstracts.rest.AbstractResponse;
//import com.starksoftware.library.security.dto.UsuarioDTO;
//import com.starksoftware.library.security.model.annotation.Secured;
//import com.starksoftware.library.security.model.business.Kryptonite;
//import com.starksoftware.library.security.model.business.TokenHandler;
//import com.starksoftware.library.security.model.business.UsuarioFacade;
//import com.starksoftware.library.security.model.entity.PasswordHashedAndSalt;
//import com.starksoftware.library.security.model.entity.Usuario;
//import com.starksoftware.library.security.util.CommonSecurity;
//import com.starksoftware.library.util.SistemaUtil;
//import com.starksoftware.library.util.ValidatorUtil;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//
//@Api(value = "Login")
//@Path("/login")
//public class LoginWS extends AbstractResponse {
//
//	private static final Logger LOG = Logger.getLogger(LoginWS.class.getName());
//	private static final String MSG_ERRO = "MSG Erro: %s";
//	private static final String ROOT_PATH = "/";
//	private static final String MSG_EXPIROU = "Seu período para acessar o sistema expirou, favor entrar em contato com o administrador do sistema.";
//
//	@Inject
//	private UsuarioFacade usuarioService;
//	
//	@Inject
//	private TokenHandler tokenHandler;
//
//	@Context
//	UriInfo uriInfo;
//
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Serviço de login", notes = "Informar usuário e senha para autenticação")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
//			@ApiResponse(code = 406, message = "Erro de validação interna"),
//			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
//	public Response login(@ApiParam(value = "JSON", required = true) Usuario usuario) {
//
//		try {
//			validarCredenciaisInformada(usuario);
//			Usuario usuDb = obterInformacoesDoUsuario(usuario);
//			validarSenhaInformada(usuario, usuDb);
//
//			final String token = tokenHandler.createTokenForUser(usuario.getLogin(), usuDb.getRoleAccess());
//
//			UsuarioDTO usuarioDTO = new UsuarioDTO();
//			usuarioDTO.setNome(usuDb.getNome());
//			usuarioDTO.setId(usuDb.getId());
//			usuarioDTO.setToken(token);
//			usuarioDTO.setEmail(usuDb.getEmail());
//
//			if (usuario.getDeviceToken() != null) {
//				usuarioService.limaparTokens(usuario);
//			}
//
//			if (usuario.getDeviceToken() != null && !usuario.getDeviceToken().isEmpty()) {
//				usuDb.setDeviceToken(usuario.getDeviceToken());
//			}
//			if (usuario.getDeviceType() != null && !usuario.getDeviceType().isEmpty()) {				
//				usuDb.setDeviceType(usuario.getDeviceType());
//			}
//			usuarioService.salvarUsuario(usuDb);
//			
//			return this.ok(usuarioDTO, obterCookieComNovoToken(token));
//
//		} catch (SistemaException ex) {
//
//			LOG.log(Level.FINER, ex.getMessage(), ex);
//			return this.error(ex.getMessage());
//
//		} catch (Exception e) {
//
//			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
//			return this.internalError(null);
//		}
//	}
//
//	@GET
//	@Path("/recuperarSenha")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Serviço de recuperar senha", notes = "Informar e-mail")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
//			@ApiResponse(code = 406, message = "Erro de validação interna"),
//			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
//	public Response recuperarSenha(@ApiParam(value = "email", required = true) @QueryParam("email") String email) {
//
//		try {
//			Usuario usuario = usuarioService.pesquisarUsuariosPorEmail(email);
//			if (usuario != null) {
//				usuarioService.trocarSenhaUsuario(usuario.getId(), SistemaUtil.randomString(5));
//			} else {
//				throw new SistemaException("Não foi encontrado usuário com este email.");
//			}
//			return this.ok("OK");
//
//		} catch (Exception e) {
//
//			LOG.log(Level.SEVERE, String.format(MSG_ERRO, e.toString()), e);
//			return this.internalError(null);
//		}
//	}
//
//	@GET
//	@Secured
//	@Path("/isAutenticado")
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Serviço verificação de usuário logado", notes = "Através da anotação @Secured é checado se o usuário está autendicado.")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso! Usuário autenticado."),
//			@ApiResponse(code = 401, message = "Não autorizado") })
//	public Response isAutenticado() {
//
//		return this.ok("O usuário está autenticado.");
//	}
//
//	@GET
//	@Secured
//	@ApiOperation(value = "Serviço faz logout de usuário logado", notes = "Através da anotação @Secured é checado se o usuário está autendicado.")
//	@Path(value = "/logout")
//	public Response logout(@Context HttpServletRequest request, @CookieParam(value = "JSESSIONID") String cookie) {
//		try {
//			if (cookie.equalsIgnoreCase(request.getSession().getId())) {
//				request.getSession().invalidate();
//				Response.ok("logout com sucesso").build();
//			}
//			return Response.noContent().build();
//		} catch (NullPointerException ex) {
//			LOG.log(Level.SEVERE, String.format(MSG_ERRO, ex.toString()), ex);
//			return Response.status(Response.Status.UNAUTHORIZED).build();
//		}
//	}
//
//	/**
//	 * 
//	 * Cria cookie valido.
//	 * 
//	 * @param token
//	 * @return
//	 */
//	private NewCookie obterCookieComNovoToken(final String token) {
//
//		return new NewCookie(CommonSecurity.SESSION_ID, token, ROOT_PATH, uriInfo.getBaseUri().getHost(), "",
//				NewCookie.DEFAULT_MAX_AGE, false);
//	}
//
//	/**
//	 * 
//	 * @param credencial
//	 * @param usuDb
//	 * @throws SistemaException
//	 */
//	private void validarSenhaInformada(Usuario credencial, Usuario usuDb) throws SistemaException {
//
//		final PasswordHashedAndSalt pass = Kryptonite.hashPassword(credencial.getSenha());
//
//		if (!usuDb.getSenha().equals(pass.getPasswordHashed())) {
//			throw new SistemaException("Senha inválida");
//		}
//	}
//
//	/**
//	 * 
//	 * @param usuario
//	 * @return
//	 * @throws SistemaException
//	 */
//	private Usuario obterInformacoesDoUsuario(Usuario usuario) throws SistemaException {
//
//		Usuario usuDb = usuarioService.findByLogin(usuario.getLogin());
//
//		if (ValidatorUtil.isNull(usuDb)) {
//			throw new SistemaException("Usuário não encontrado");
//		}
//
//		return usuDb;
//	}
//
//	/**
//	 * @param usuario
//	 * @throws SistemaException
//	 */
//	private void validarCredenciaisInformada(Usuario usuario) throws SistemaException {
//
//		if (ValidatorUtil.isNull(usuario.getLogin()) || ValidatorUtil.isNull(usuario.getSenha())) {
//			throw new SistemaException("Login e senha são obrigatórios");
//		}
//	}
//
//}
