package com.starksoftware.library.security.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.dto.FilterUsuarioDTO;
import com.starksoftware.library.security.model.dao.UsuarioDAO;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.library.security.model.entity.TipoUsuario;
import com.starksoftware.stampyu.model.entity.Usuario;

@Stateless
public class UsuarioFacade extends AbstractFacade {

	@Inject
	private FrwSecurityContext frwSecurityContext;

	@Inject
	private UsuarioDAO repository;

	@Inject
	private TipoUsuarioFacade tipoUsuarioService;
	
	
	public void excluirUsuario(Usuario usuario) {
		usuario.setUsuarioAlteracao(frwSecurityContext.getCurrentUsername());
		usuario.setExcluido(true);
		repository.saveOrUpdate(usuario);
	}

	public List<Usuario> pesquisarUsuarios(FilterUsuarioDTO filter) {
		List<Usuario> retorno = repository.pesquisarUsuarios(filter);
		for (Usuario usuario : retorno) {
			Hibernate.initialize(usuario.getPerfis());
			for(Perfil perfil : usuario.getPerfis()){
				Hibernate.initialize(perfil.getFuncionalidades());
			}
		}
		return retorno;
	}

	public List<TipoUsuario> pesquisarTodosTiposUsuario() {
		return tipoUsuarioService.pesquisarTodosTiposUsuario();
	}

	public TipoUsuario buscarTipoUsuarioById(Long id) {
		return tipoUsuarioService.buscarTipoUsuarioById(id);
	}

	public Usuario findByLogin(String login) {
		return repository.findByLogin(login);
	}

	public Usuario trocarSenhaUsuario(Long id, String novaSenha) {
		Usuario usuarioDB = buscarUsuarioPorId(id);
		usuarioDB.setSenha(novaSenha);
		usuarioDB.setSenha(Kryptonite.hashPassword(novaSenha).getPasswordHashed());
		salvarUsuario(usuarioDB);
		return usuarioDB;
	}
	
	public Usuario buscarUsuarioPorId(Long id) {
		return repository.findByPrimaryKey(id);
	}

	public void salvarUsuario(Usuario usuario) {
		repository.saveOrUpdate(usuario);
	}

	public void limaparTokens(Usuario usuario) {
		List<Usuario> retorno = repository.limaparTokens(usuario);

		for (Usuario usuario2 : retorno) {
			usuario2.setDeviceToken(null);
			repository.saveOrUpdate(usuario2);
		}

	}
	
	public Usuario pesquisarUsuariosPorEmail(String email) {
		return repository.pesquisarUsuariosPorEmail(email);
	}
}
