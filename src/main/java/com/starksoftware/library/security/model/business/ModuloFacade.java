package com.starksoftware.library.security.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.model.dao.ModuloDAO;
import com.starksoftware.library.security.model.entity.Modulo;
import com.starksoftware.library.security.model.entity.Usuario;

@Stateless
public class ModuloFacade extends AbstractFacade {

	@Inject
	private FrwSecurityContext frwSecurityContext;

	@Inject
	private ModuloDAO repository;

	public List<Modulo> buscarModulos(Usuario usuario) {
		return repository.buscarTodos();
	}

	public void excluirModulo(Modulo modulo) {
		modulo.setUsuarioAlteracao(frwSecurityContext.getCurrentUsername());
		modulo.setExcluido(true);
		repository.saveOrUpdate(modulo);
	}

	public List<Modulo> buscarTodos() {
		return repository.buscarTodos();
	}

	public Modulo buscarModuloPorId(Long id) {
		return repository.findByPrimaryKey(id);
	}

	public void salvarModulo(Modulo modulo) {
		repository.saveOrUpdate(modulo);
	}
}
