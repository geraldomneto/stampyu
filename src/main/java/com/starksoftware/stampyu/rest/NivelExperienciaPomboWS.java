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

import com.starksoftware.library.abstracts.rest.AbstractRest;
import com.starksoftware.stampyu.dto.NivelExperienciaPomboDTO;
import com.starksoftware.stampyu.model.business.NivelExperienciaPomboFacade;
import com.starksoftware.stampyu.model.entity.NivelExperienciaPombo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "tipo-postagem")
@Path("/tipo-postagem")
public class NivelExperienciaPomboWS extends AbstractRest<NivelExperienciaPomboFacade, NivelExperienciaPomboDTO> {
	
	@Inject
	private NivelExperienciaPomboFacade nivelExperienciaPomboFacade;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca todos os níveis de experiência que Pombos podem ter")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response get() {
		try {
			List<NivelExperienciaPombo> list = nivelExperienciaPomboFacade.findAll();
			List<NivelExperienciaPomboDTO> retorno = new ArrayList<>();
			for (NivelExperienciaPombo item : list) {
				retorno.add(new NivelExperienciaPomboDTO(item));
			}
			return this.ok(retorno);

		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca NivelExperienciaPombo por id")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response getPorId(@ApiParam(value = "id") @PathParam("id") String strId) {
		try {
			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}
			NivelExperienciaPombo nivelExperienciaPombo = nivelExperienciaPomboFacade.findByPrimaryKey(id);
			if (nivelExperienciaPombo != null) {
				return this.ok(new NivelExperienciaPomboDTO(nivelExperienciaPombo));
			} else {
				return this.error("Nível de Experiência não encontrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastra um novo nível de experiência que pombos podem ter")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response post(@ApiParam(value = "JSON", required = true) NivelExperienciaPomboDTO dto) {
		try {
			if (dto.getDescricao() == null) {
				return this.error("Informe a descrição");
			}
			if (dto.getVelocidadeKmPorHora() == null) {
				return this.error("Informe a velocidade");
			}
			NivelExperienciaPombo nivelExperienciaPombo = new NivelExperienciaPombo(dto);
			nivelExperienciaPomboFacade.saveOrUpdate(nivelExperienciaPombo);

			return this.created("Nível de experiência cadastrado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza atributos do NivelExperienciaPombo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response put(@ApiParam(value = "JSON", required = true) NivelExperienciaPomboDTO dto) {
		try {
			if (dto.getId() == null) {
				return this.error("Informe o id");
			}
			if (dto.getDescricao() == null) {
				return this.error("Informe a descrição");
			}
			if (dto.getVelocidadeKmPorHora() == null) {
				return this.error("Informe a velocidade");
			}

			NivelExperienciaPombo nivelExperienciaPombo = nivelExperienciaPomboFacade.findByPrimaryKey(dto.getId());
			
			if (nivelExperienciaPombo == null) {
				return this.error("Nível de experiência não encontrado");
			} else {
				nivelExperienciaPombo.setDescricao(dto.getDescricao());
				nivelExperienciaPombo.setVelocidadeKmPorHora(dto.getVelocidadeKmPorHora());
				nivelExperienciaPomboFacade.saveOrUpdate(nivelExperienciaPombo);
				return this.created("Nível de experiência atualizado com sucesso!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Exclui um NivelExperienciaPombo pelo id", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response delete(@ApiParam(value = "ID") @PathParam("id") Long id) {
		try {
			NivelExperienciaPombo nivelExperienciaPombo = nivelExperienciaPomboFacade.findByPrimaryKey(id);
			if (nivelExperienciaPombo == null) {
				return this.error("Nível de Experiência não encontrado");
			} else {
				nivelExperienciaPomboFacade.delete(nivelExperienciaPombo);
				return this.successWithoutBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}
}
