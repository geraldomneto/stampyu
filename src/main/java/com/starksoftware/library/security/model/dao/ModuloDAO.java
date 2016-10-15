package com.starksoftware.library.security.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.starksoftware.library.abstracts.model.dao.BaseDAO;
import com.starksoftware.library.security.model.entity.Modulo;

@RequestScoped
@SuppressWarnings("unchecked")
public class ModuloDAO extends BaseDAO<Modulo> {

	public Modulo getModuloDefault() {

		return (Modulo) em.createQuery("select m from Modulo m order by m.id ").getResultList().get(0);
	}

	public List<Modulo> buscarTodos() {
		try {
			return em.createQuery("select m from Modulo m where m.excluido <> true order by m.ordemExibicao")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Modulo>();
		}
	}
}
