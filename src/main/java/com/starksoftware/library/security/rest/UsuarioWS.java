package com.starksoftware.library.security.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.starksoftware.library.abstracts.model.exception.SistemaException;
import com.starksoftware.library.abstracts.rest.AbstractRest;
import com.starksoftware.library.security.dto.FilterUsuarioDTO;
import com.starksoftware.library.security.dto.TipoUsuarioDTO;
import com.starksoftware.library.security.dto.UsuarioDTO;
import com.starksoftware.library.security.model.business.Kryptonite;
import com.starksoftware.library.security.model.business.PerfilFacade;
import com.starksoftware.library.security.model.business.UsuarioFacade;
import com.starksoftware.library.security.model.entity.TipoUsuario;
import com.starksoftware.library.security.model.entity.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "usuario")
@Path("/usuario")
// @Secured
public class UsuarioWS extends AbstractRest<UsuarioFacade, UsuarioDTO> {

	private static Logger LOG = Logger.getLogger(UsuarioWS.class.getName());

	@Inject
	private UsuarioFacade usuarioService;

	@Inject
	private PerfilFacade perfilService;

	@GET
	@Path("/tipoUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todos os tipos de usuários", notes = "Todos os tipos de usuarios cadastros, não existe cadastro de Tipo de Usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarTodosTipoUsuario() {
		try {
			List<TipoUsuario> listTipoUsuario = usuarioService.pesquisarTodosTiposUsuario();
			List<TipoUsuarioDTO> listRetorno = new ArrayList<>();
			for (TipoUsuario tipoUsuario : listTipoUsuario) {
				listRetorno.add(new TipoUsuarioDTO(tipoUsuario));
			}
			return this.ok(listRetorno);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@GET
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todos os usuarios do sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarUsuarioAdmin(@ApiParam(value = "nome") @QueryParam("nome") String nome,
			@ApiParam(value = "login") @QueryParam("login") String login) {
		try {

			FilterUsuarioDTO filter = new FilterUsuarioDTO();
			filter.setNome(nome);
			filter.setLogin(login);
			filter.setIsAdmin(true);

			List<Usuario> listUsuario = usuarioService.pesquisarUsuarios(filter);
			List<UsuarioDTO> listRetorno = new ArrayList<>();
			for (Usuario usuario : listUsuario) {
				listRetorno.add(new UsuarioDTO(usuario));
			}
			return this.ok(listRetorno);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todos os usuarios do sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarUsuario(@ApiParam(value = "nome") @QueryParam("nome") String nome,
			@ApiParam(value = "login") @QueryParam("login") String login) {
		try {

			FilterUsuarioDTO filter = new FilterUsuarioDTO();
			filter.setNome(nome);
			filter.setLogin(login);
			filter.setIsAdmin(false);

			List<Usuario> listUsuario = usuarioService.pesquisarUsuarios(filter);
			
//			List<UsuarioDTO> listRetorno = new ArrayList<>();
//			for (Usuario usuario : listUsuario) {
//				listRetorno.add(new UsuarioDTO(usuario));
//			}
			return this.ok(listUsuario);

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera usuário por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarUsuarioPorId(@ApiParam(value = "id") @PathParam("id") String strId) {
		try {

			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}
			return this.ok(new UsuarioDTO(usuarioService.buscarUsuarioPorId(id)));

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastro de Usuario", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastroUsuario(@ApiParam(value = "JSON", required = true) Usuario usuario) {
		try {

			validaDadosUsuario(usuario);
			usuario.setSenha(Kryptonite.hashPassword(usuario.getSenha()).getPasswordHashed());
			usuario.setAtivo(true);
			usuarioService.salvarUsuario(usuario);

			return this.created("Usuário cadastrado com sucesso");
		} catch (SistemaException ex) {
			return this.error(ex.getMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualização de Usuario", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response atualizacaoUsuario(@ApiParam(value = "JSON", required = true) Usuario usuario) {
		try {

			validaDadosUsuarioUpdate(usuario);
			usuarioService.salvarUsuario(usuario);

			return this.ok("Usuario atualizado com sucesso");
		} catch (SistemaException ex) {
			return this.error(ex.getMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{idDelete}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Remove usuario do sistema", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response deleteUsuario(@ApiParam(value = "ID") @PathParam("idDelete") String strId) {
		try {

			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}

			Usuario usuario = usuarioService.buscarUsuarioPorId(id);
			if (usuario == null) {
				return this.error("Usuário não encontrado");
			}
			usuarioService.excluirUsuario(usuario);
			return this.successWithoutBody();
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	private void validaDadosUsuario(Usuario usuario) throws SistemaException {
		if (usuario.getTipoUsuario() == null) {
			usuario.setTipoUsuario(usuarioService.buscarTipoUsuarioById(2l));
		}
		if (usuario.getNome() == null) {
			throw new SistemaException("Informe o nome");
		}
		if (usuario.getEmail() == null) {
			throw new SistemaException("Informe o e-mail");
		}
		if (usuario.getSenha() == null) {
			throw new SistemaException("Informe a senha");
		}
		
		if (usuario.getPerfis().isEmpty()) {
			usuario.getPerfis().add(perfilService.pesquisarPerfilPorId(2l));
		}
	}

	private void validaDadosUsuarioUpdate(Usuario usuario) throws SistemaException {

		Usuario dbUsuario = usuarioService.buscarUsuarioPorId(usuario.getId());

		if (usuario.getTipoUsuario() == null) {
			throw new SistemaException("Informe o tipo do usuário");
		}
		if (usuario.getNome() == null) {
			throw new SistemaException("Informe o nome");
		}
		
		if (usuario.getSenha() == null) {
			throw new SistemaException("Informe a senha");
		}
		if (usuario.getPerfis().isEmpty()) {
			throw new SistemaException("Informe pelo menos um perfil");
		}

		if (!usuario.getLogin().equals(dbUsuario.getLogin())) {
			throw new SistemaException("Login não pode alterar");
		}

	}
}
