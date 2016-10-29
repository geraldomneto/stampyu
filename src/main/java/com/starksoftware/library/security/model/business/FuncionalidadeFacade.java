package com.starksoftware.library.security.model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.library.security.model.dao.FuncionalidadeDAO;
import com.starksoftware.library.security.model.entity.Funcionalidade;
import com.starksoftware.library.security.model.entity.Modulo;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.stampyu.model.entity.Usuario;

@Stateless
public class FuncionalidadeFacade extends AbstractFacade {

	@Inject
	private FrwSecurityContext frwSecurityContext;

	@Inject
	private PerfilFacade perfilService;

	@Inject
	private FuncionalidadeDAO repository;

	public Map<Long, Funcionalidade> pesquisarFuncionalidadesPorUsuario(Usuario usuario, Modulo modulo) {

		// Funcionalidade funcionalidade = new Funcionalidade();
		Map<Long, Funcionalidade> funcionalidadesMap = new HashMap<Long, Funcionalidade>();

		List<Perfil> perfis = perfilService.findPerfisByUsuario(usuario);
		for (Perfil perfil : perfis) {
			List<Funcionalidade> funcionalidades = repository.findFuncionalidadesByPerfilAndModulo(perfil, modulo);

			for (Funcionalidade func : funcionalidades) {
				funcionalidadesMap.put(func.getId(), func);
			}
		}
		return funcionalidadesMap;
	}

	public List<Funcionalidade> pesquisarTodasFuncionalidades() {

		// Funcionalidade funcionalidade = new Funcionalidade();

		List<Funcionalidade> list = new ArrayList<Funcionalidade>();
		for (Funcionalidade fun : repository.findAllFuncionalidades()) {
			Funcionalidade func = new Funcionalidade();
			func.setId(fun.getId());
			func.setDescricao(fun.getDescricao());
			func.setDescricaoResumida(fun.getDescricaoResumida());
			func.setUrl(fun.getUrl());
			func.setOrdemExibicao(fun.getOrdemExibicao());

			if (fun.getModulo() != null) {
				Modulo modulo = new Modulo();
				modulo.setId(fun.getModulo().getId());
				modulo.setNome(fun.getModulo().getNome());
				func.setModulo(modulo);
			}


			list.add(func);
		}
		return list;
	}

	public void excluirFuncionalidade(Funcionalidade funcionalidade) {
		funcionalidade.setUsuarioAlteracao(frwSecurityContext.getCurrentUsername());
		funcionalidade.setExcluido(true);
		repository.saveOrUpdate(funcionalidade);
	}

	public List<Funcionalidade> findFuncionalidadesByPerfilAndModulo(Perfil perfil, Modulo modulo) {
		return repository.findFuncionalidadesByPerfilAndModulo(perfil, modulo);
	}

	public List<Funcionalidade> findAllFuncionalidades() {
		return repository.findAllFuncionalidades();
	}

	public Funcionalidade buscarPorId(Long id) {
		return repository.findByPrimaryKey(id);
	}

	public void salvarFuncionalidade(Funcionalidade funcionalidade) {
		repository.saveOrUpdate(funcionalidade);
	}
}
