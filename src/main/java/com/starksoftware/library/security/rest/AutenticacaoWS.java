package com.starksoftware.library.security.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.starksoftware.library.abstracts.rest.AbstractResponse;
import com.starksoftware.library.security.dto.FuncionalidadeDTO;
import com.starksoftware.library.security.dto.ModuloDTO;
import com.starksoftware.library.security.model.annotation.Secured;
import com.starksoftware.library.security.model.business.FrwSecurityContext;
import com.starksoftware.library.security.model.business.FuncionalidadeFacade;
import com.starksoftware.library.security.model.business.ModuloFacade;
import com.starksoftware.library.security.model.business.UsuarioFacade;
import com.starksoftware.library.security.model.entity.Funcionalidade;
import com.starksoftware.library.security.model.entity.Modulo;
import com.starksoftware.library.security.model.entity.Usuario;
import com.starksoftware.library.security.model.enumeration.RoleAccess;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "autenticacao")
@Path("/autenticacao")
@Secured
public class AutenticacaoWS extends AbstractResponse {

	private static Logger LOG = Logger.getLogger(AutenticacaoWS.class.getName());

	@Inject
	private UsuarioFacade usuarioService;

	@Inject
	private ModuloFacade moduloService;

	@Inject
	protected FuncionalidadeFacade funcionalidadeService;

	@Inject
	private FrwSecurityContext frwSecurityContext;

	/**
	 * MENU
	 */
	@GET
	@Path("/menu")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Recupera o menu do usuário", notes = "PathParam - Obrigatório enviar o ID do usuário")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso"),
			@ApiResponse(code = 401, message = "Não autorizado"), @ApiResponse(code = 403, message = "Acesso negado"),
			@ApiResponse(code = 500, message = "Erro ao processar sua requisição") })
	@Secured(RoleAccess.NONE)
	public Response buscarUsuario() {
		try {

			Usuario usuario = usuarioService.findByLogin(frwSecurityContext.getCurrentUsername());

			if (usuario == null) {
				return this.error("Usuário não encontrado");
			}

			List<Modulo> listModulos = moduloService.buscarModulos(usuario);
			List<ModuloDTO> listRetorno = new ArrayList<>();
			for (Modulo modulo : listModulos) {
				ModuloDTO entity = new ModuloDTO();
				entity.setId(modulo.getId());
				entity.setNome(modulo.getNome());
				entity.setOrdemExibicao(modulo.getOrdemExibicao());
				entity.setFuncionalidades(new ArrayList<FuncionalidadeDTO>());
				for (Entry<Long, Funcionalidade> hashFunc : funcionalidadeService
						.pesquisarFuncionalidadesPorUsuario(usuario, modulo).entrySet()) {
					entity.getFuncionalidades().add(new FuncionalidadeDTO(hashFunc.getValue()));
				}
				listRetorno.add(entity);
			}
			return this.ok(listRetorno);

		} catch (Exception e) {
			LOG.error(e.toString());
			return this.internalError(null);
		}
	}
}
