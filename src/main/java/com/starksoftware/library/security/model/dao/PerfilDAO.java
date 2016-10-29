package com.starksoftware.library.security.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.starksoftware.library.abstracts.model.dao.BaseDAO;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.library.security.model.entity.TipoUsuario;
import com.starksoftware.stampyu.model.entity.Usuario;

@RequestScoped
@SuppressWarnings("unchecked")
public class PerfilDAO extends BaseDAO<Perfil> {

	public List<Perfil> findByTipoUsuario(TipoUsuario tipo) {
		return em.createNamedQuery("findPerfilByTipoUsuario").setParameter("tipo", tipo).getResultList();
	}

	public List<Perfil> findPerfisByUsuario(Usuario usuario) {
		try {
			return em
					.createQuery(
							"select p from Usuario u join u.perfis p where p.excluido <> true and u.id = :idUsuario")
					.setParameter("idUsuario", usuario.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Perfil>();
		}
	}

	public List<Perfil> findPerfisByTipo(TipoUsuario tipoUsuario) {
		try {
			return em.createQuery("select p from Perfil p where p.tipoUsuario.id = :idIipoUsuario")
					.setParameter("idIipoUsuario", tipoUsuario.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Perfil>();
		}
	}

	public List<Perfil> pesquisarTodosPerfis() {
		try {
			return em.createQuery("select p from Perfil p where p.excluido <> true").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Perfil>();
		}
	}
}
