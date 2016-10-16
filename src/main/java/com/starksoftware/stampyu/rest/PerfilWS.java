package com.starksoftware.stampyu.rest;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.dto.PerfilDTO;
import com.starksoftware.library.security.model.business.PerfilFacade;
import com.starksoftware.library.security.model.entity.Perfil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "perfil")
@Path("/perfil")
// @Secured
public class PerfilWS extends AbstractResponse {

	private static Logger LOG = Logger.getLogger(PerfilWS.class.getName());

	@Inject
	private PerfilFacade perfilService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todas perfis do sistema", notes = "Todas as perfis")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarPerfil() {
		try {

			List<Perfil> listPerfil = perfilService.pesquisarTodosPerfis();
			List<PerfilDTO> retorno = new ArrayList<>();
			for (Perfil perfil : listPerfil) {
				retorno.add(new PerfilDTO(perfil));
			}
			return this.ok(retorno);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todas perfils do sistema por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarPerfilPorId(@ApiParam(value = "id") @PathParam("id") Long id) {
		try {
			return this.ok(new PerfilDTO(perfilService.pesquisarPerfilPorId(id)));

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastro de perfil", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastroPerfil(@ApiParam(value = "JSON", required = true) Perfil perfil) {
		try {
			perfilService.salvarPerfil(perfil);
			return this.created("Perfil cadastrado com sucesso!");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualização de perfil", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response atualizaPerfil(@ApiParam(value = "JSON", required = true) Perfil perfil) {
		try {
			perfilService.salvarPerfil(perfil);
			return this.created("Perfil salvo com sucesso!");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Remove perfil do sistema", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response deletePerfil(@ApiParam(value = "ID") @PathParam("id") Long id) {
		try {
			Perfil perfil = perfilService.pesquisarPerfilPorId(id);

			if (perfil == null) {
				return this.error("Perfil não encontrado");
			}
			perfilService.excluirPerfil(perfil);
			return this.successWithoutBody();
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}
}
