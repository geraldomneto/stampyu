package com.starksoftware.stampyu.rest;

import java.util.ArrayList;
import java.util.Base64;
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
import com.starksoftware.stampyu.dto.SeloDTO;
import com.starksoftware.stampyu.model.business.SeloFacade;
import com.starksoftware.stampyu.model.entity.Selo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "selo")
@Path("/selo")
public class SeloWS extends AbstractRest<SeloFacade, SeloDTO> {
	
	@Inject
	private SeloFacade seloFacade;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca todos os selos")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response getSelo() {
		try {
			List<Selo> listSelo = seloFacade.findAll();
			List<SeloDTO> retorno = new ArrayList<>();
			for (Selo selo : listSelo) {
				retorno.add(new SeloDTO(selo));
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
	@ApiOperation(value = "Busca Selo por id", notes = "Busca por ID")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response getSeloPorId(@ApiParam(value = "id") @PathParam("id") String strId) {
		try {
			Long id = null;
			try {
				id = Long.parseLong(strId);
			} catch (Exception ex) {
				return this.error("Informe um ID numerico válido");
			}
			Selo selo = seloFacade.findByPrimaryKey(id);
			if (selo != null) {
				return this.ok(new SeloDTO(selo));
			} else {
				return this.error("Selo não encontrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cadastra um novo Selo")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Criado com sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), 
			@ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response cadastraSelo(@ApiParam(value = "JSON", required = true) SeloDTO dto) {
		try {
			if (dto.getNumeroSerie() == null) {
				return this.error("Informe o número de série");
			}
			if (dto.getTitulo() == null) {
				return this.error("Informe o título");
			}
			if (dto.getImagem() == null) {
				return this.error("Informe a imagem");
			}
			Selo selo = new Selo();
			selo.setNumeroSerie(dto.getNumeroSerie());
			selo.setTitulo(dto.getTitulo());
			selo.setBackgroundColorHexa(dto.getBackgroundColorHexa());
			selo.setImagem(Base64.getDecoder().decode(dto.getImagem()));
			
			seloFacade.saveOrUpdate(selo);

			return this.created("Selo cadastrado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Atualiza atributos do Selo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 406, message = "Erro de validação interna"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response atualizaSelo(@ApiParam(value = "JSON", required = true) SeloDTO dto) {
		try {
			if (dto.getId() == null) {
				return this.error("Informe o id do Selo");
			}
			if (dto.getNumeroSerie() == null) {
				return this.error("Informe o número de série");
			}
			if (dto.getTitulo() == null) {
				return this.error("Informe o título");
			}
			if (dto.getImagem() == null) {
				return this.error("Informe a imagem");
			}
			
			Selo selo = seloFacade.findByPrimaryKey(dto.getId());
			
			if (selo == null) {
				return this.error("Selo não encontrado");
			} else {
				if (dto.getNumeroSerie() != null) {
					selo.setNumeroSerie(dto.getNumeroSerie());					
				}
				if (dto.getTitulo() != null) {
					selo.setTitulo(dto.getTitulo());					
				}
				if (dto.getBackgroundColorHexa() != null) {
					selo.setBackgroundColorHexa(dto.getBackgroundColorHexa());					
				}
				if (dto.getImagem() != null) {
					selo.setImagem(Base64.getDecoder().decode(dto.getImagem()));					
				}
				seloFacade.saveOrUpdate(selo);
				return this.created("Selo atualizado com sucesso!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Exclui um Selo pelo id", notes = "Exclusão é lógica")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	public Response deleteSelo(@ApiParam(value = "ID") @PathParam("id") Long id) {
		try {
			Selo selo = seloFacade.findByPrimaryKey(id);
			if (selo == null) {
				return this.error("Selo não encontrado");
			} else {
				seloFacade.delete(selo);
				return this.successWithoutBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return this.internalError(null);
		}
	}
}
