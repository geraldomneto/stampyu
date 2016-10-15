package com.starksoftware.library.security.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.Query;

import com.starksoftware.library.abstracts.model.dao.BaseDAO;
import com.starksoftware.library.security.model.entity.TipoUsuario;

@RequestScoped
@SuppressWarnings("unchecked")
public class TipoUsuarioDAO extends BaseDAO<TipoUsuario> {

	public List<TipoUsuario> pesquisarTodosTiposUsuario() {

		StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT u FROM TipoUsuario u ");
		queryBuilder.append(" WHERE u.excluido <> true order by u.descricao ");

		Query query = em.createQuery(queryBuilder.toString());

		Map<String, Object> parameters = new HashMap<String, Object>();
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		return query.getResultList();
	}
}
