package com.starksoftware.stampyu.rest;

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
import com.starksoftware.library.security.dto.FuncionalidadeDTO;
import com.starksoftware.library.security.model.annotation.Secured;
import com.starksoftware.library.security.model.business.FuncionalidadeFacade;
import com.starksoftware.library.security.model.entity.Funcionalidade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "funcionalidade")
@Path("/funcionalidade")
@Secured
public class FuncionalidadeWS extends AbstractResponse {

	private static Logger LOG = Logger.getLogger(FuncionalidadeWS.class.getName());

	@Inject
	private FuncionalidadeFacade funcionalidadeService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todas funcionalidades do sistema", notes = "Todas as funcionalidades")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarFuncionalidade() {
		try {

			return this.ok(funcionalidadeService.pesquisarTodasFuncionalidades());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera todas funcionalidades do sistema por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response buscarFuncionalidadePorId(@ApiParam(value = "id") @PathParam("id") Long id) {
		try {

			return this.ok(new FuncionalidadeDTO(funcionalidadeService.buscarPorId(id)));

		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastro de funcionalidade", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastroFuncionalidade(@ApiParam(value = "JSON", required = true) Funcionalidade funcionalidade) {
		try {
			if (funcionalidade.getModulo() == null) {
				return this.error("Informe o modulo");
			}
			if (funcionalidade.getOrdemExibicao() == null) {
				return this.error("Informe a ordem de exibição");
			}
			if (funcionalidade.getDescricao() == null) {
				return this.error("Informe a descrição");
			}
			if (funcionalidade.getDescricaoResumida() == null) {
				return this.error("Informe a descrição resumida");
			}

			funcionalidade.setItemMenu(true);
			funcionalidadeService.salvarFuncionalidade(funcionalidade);

			return this.created("Funcionalidade cadastrada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualização de funcionalidade", notes = "")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response atualizaFuncionalidade(@ApiParam(value = "JSON", required = true) Funcionalidade funcionalidade) {
		try {
			if (funcionalidade.getModulo() == null) {
				return this.error("Informe o modulo");
			}
			if (funcionalidade.getOrdemExibicao() == null) {
				return this.error("Informe a ordem de exibição");
			}
			if (funcionalidade.getDescricao() == null) {
				return this.error("Informe a descrição");
			}
			if (funcionalidade.getDescricaoResumida() == null) {
				return this.error("Informe a descrição resumida");
			}

			funcionalidade.setItemMenu(true);
			funcionalidadeService.salvarFuncionalidade(funcionalidade);

			return this.created("Funcionalidade salvo com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Remove funcionalidade do sistema", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response deleteFuncionalidade(@ApiParam(value = "ID") @PathParam("id") String strId) {
		try {

			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}

			Funcionalidade funcionalidade = funcionalidadeService.buscarPorId(id);
			if (funcionalidade == null) {
				return this.error("Funcionalidade não encontrada");
			}
			funcionalidadeService.excluirFuncionalidade(funcionalidade);
			return this.successWithoutBody();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getLocalizedMessage());
			return this.internalError(null);
		}
	}
}
