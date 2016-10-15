package com.starksoftware.library.security.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.model.dao.TipoUsuarioDAO;
import com.starksoftware.library.security.model.entity.TipoUsuario;

@Stateless
public class TipoUsuarioFacade extends AbstractFacade {

	@Inject
	private TipoUsuarioDAO repository;

	public List<TipoUsuario> pesquisarTodosTiposUsuario() {
		return repository.pesquisarTodosTiposUsuario();
	}

	public TipoUsuario buscarTipoUsuarioById(Long id) {
		return repository.findByPrimaryKey(id);
	}
}
