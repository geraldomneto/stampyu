package com.starksoftware.library.security.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.Hibernate;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.model.dao.PerfilDAO;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.stampyu.model.entity.Usuario;

@Stateless
public class PerfilFacade extends AbstractFacade {

	@Inject
	private FrwSecurityContext frwSecurityContext;

	@Inject
	private PerfilDAO repository;

	public void excluirPerfil(Perfil perfil) {
		perfil.setUsuarioAlteracao(frwSecurityContext.getCurrentUsername());
		perfil.setExcluido(true);
		repository.saveOrUpdate(perfil);
	}

	public List<Perfil> findPerfisByUsuario(Usuario usuario) {
		return repository.findPerfisByUsuario(usuario);
	}

	public List<Perfil> pesquisarTodosPerfis() {
		List<Perfil> perfis = repository.pesquisarTodosPerfis();
		for(Perfil perfil : perfis){
			Hibernate.initialize(perfil.getFuncionalidades());
		}
		
		return perfis;
	}

	public Perfil pesquisarPerfilPorId(Long id) {
		return repository.findByPrimaryKey(id);
	}

	public void salvarPerfil(Perfil perfil) {
		repository.saveOrUpdate(perfil);
	}
}
