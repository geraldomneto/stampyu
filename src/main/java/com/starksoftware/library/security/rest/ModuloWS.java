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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.dto.ModuloDTO;
import com.starksoftware.library.security.model.business.ModuloFacade;
import com.starksoftware.library.security.model.entity.Modulo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "modulo")
@Path("/modulo")
// @Secured
public class ModuloWS extends AbstractResponse {

	private static Logger LOG = Logger.getLogger(ModuloWS.class.getName());

	@Inject
	private ModuloFacade moduloService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todos os modulos de menu do sistema")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarModulo() {
		try {

			List<Modulo> listModulo = moduloService.buscarTodos();
			List<ModuloDTO> retorno = new ArrayList<>();
			for (Modulo modulo : listModulo) {
				retorno.add(new ModuloDTO(modulo));
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
	@ApiOperation(value = "Recupera modulo de menu do sistema por id", notes = "Busca por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarModuloId(@ApiParam(value = "id") @PathParam("id") String strId) {
		try {

			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}

			return this.ok(new ModuloDTO(moduloService.buscarModuloPorId(id)));

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastro de modulo", notes = "'ordemExibicao' e 'nome' são obrigatórios")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastroModulo(@ApiParam(value = "JSON", required = true) Modulo modulo) {
		try {
			if (modulo.getNome() == null) {
				return this.error("Informe o nome");
			}

			if (modulo.getOrdemExibicao() == null) {
				return this.error("Informe a ordem de exibição");
			}

			moduloService.salvarModulo(modulo);

			return this.created("Modulo cadastrado com sucesso!");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza modulo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response atualizaModulo(@ApiParam(value = "JSON", required = true) Modulo modulo) {
		try {
			if (modulo.getNome() == null) {
				return this.error("Informe o nome");
			}

			if (modulo.getOrdemExibicao() == null) {
				return this.error("Informe a ordem de exibição");
			}

			moduloService.salvarModulo(modulo);

			return this.ok("Modulo atualizado com sucesso");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Remove modulo do sistema", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response deleteModulo(@ApiParam(value = "ID") @PathParam("id") Long id) {
		try {

			Modulo modulo = moduloService.buscarModuloPorId(id);
			if (modulo == null) {
				return this.error("Modulo não encontrado");
			}
			moduloService.excluirModulo(modulo);
			return this.successWithoutBody();
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}
}
