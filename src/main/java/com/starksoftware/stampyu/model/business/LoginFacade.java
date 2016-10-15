package com.starksoftware.stampyu.model.business;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.model.business.PerfilFacade;
import com.starksoftware.library.security.model.business.UsuarioFacade;
import com.starksoftware.library.security.model.dao.UsuarioDAO;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.library.security.model.entity.Usuario;
import com.starksoftware.stampyu.dto.UsuarioDTO;

@Stateless
public class LoginFacade extends AbstractFacade {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private UsuarioFacade usuarioFacade;
	
	@Inject
	private PerfilFacade perfilFacade;
	
	public Usuario buscaUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioDAO.findByLogin(usuarioDTO.getLogin());
		return usuario;
	}
	
	public Usuario cadastraUsuario(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioDTO.getLogin());
		usuario.setDeviceToken(usuarioDTO.getDeviceToken());
		usuario.setDeviceType(usuarioDTO.getDeviceType());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setTipoUsuario(usuarioFacade.buscarTipoUsuarioById(usuarioDTO.getTipoUsuario()));
		usuario.setPerfis(new ArrayList<Perfil>());
		usuario.getPerfis().add(perfilFacade.pesquisarPerfilPorId(2l));
		usuarioFacade.salvarUsuario(usuario);
		return usuario;
	}
}
