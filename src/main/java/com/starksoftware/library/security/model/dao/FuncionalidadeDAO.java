package com.starksoftware.library.security.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.starksoftware.library.abstracts.model.dao.BaseDAO;
import com.starksoftware.library.security.model.entity.Funcionalidade;
import com.starksoftware.library.security.model.entity.Modulo;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.library.security.model.entity.TipoUsuario;

@RequestScoped
@SuppressWarnings("unchecked")
public class FuncionalidadeDAO extends BaseDAO<Funcionalidade> {

	public List<Funcionalidade> findFuncionalidadesByTipoUsuario(TipoUsuario tipo) {
		return em.createNamedQuery("findFuncionalidadesByTipoUsuario").setParameter("tipo", tipo.getId())
				.getResultList();
	}

	public List<Funcionalidade> findFuncionalidadesByTipoUsuarioEModulo(TipoUsuario tipo, Modulo modulo) {
		return em.createNamedQuery("findFuncionalidadesByTipoUsuarioEModulo").setParameter("tipo", tipo.getId())
				.setParameter("modulo", modulo.getId()).getResultList();
	}

	public List<Funcionalidade> findAllFuncionalidades() {
		try {
			return em.createQuery(" SELECT f FROM Funcionalidade f WHERE f.excluido <> true ORDER BY f.descricao")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Funcionalidade> findFuncionalidadesByModulo(Modulo modulo) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder queryBuilder = new StringBuilder("SELECT f FROM Funcionalidade f ");
		queryBuilder.append(" WHERE f.excluido <> true ");
		if (modulo != null) {
			queryBuilder.append(" AND f.modulo.id = :idModulo ");
			parameters.put("idModulo", modulo.getId());
		}
		queryBuilder.append(" ORDER BY f.descricao ");

		Query query = em.createQuery(queryBuilder.toString());
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Funcionalidade> findFuncionalidadesByPerfil(Perfil perfil) {
		try {
			return em
					.createQuery(
							"SELECT f from Perfil p join p.funcionalidades f where f.excluido <> true and p.id = :idPerfil")
					.setParameter("idPerfil", perfil.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Funcionalidade>();
		}
	}

	public List<Funcionalidade> findFuncionalidadesByPerfilAndModulo(Perfil perfil, Modulo modulo) {
		try {
			return em
					.createQuery(
							"SELECT f from Perfil p join p.funcionalidades f where f.excluido <> true and p.id = :idPerfil and f.modulo.id = :idModulo ")
					.setParameter("idPerfil", perfil.getId()).setParameter("idModulo", modulo.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Funcionalidade>();
		}
	}
}
