package com.starksoftware.library.security.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.starksoftware.library.abstracts.model.dao.BaseDAO;
import com.starksoftware.library.security.dto.FilterUsuarioDTO;
import com.starksoftware.library.security.model.entity.Funcionalidade;
import com.starksoftware.library.security.model.entity.Modulo;
import com.starksoftware.stampyu.model.entity.Usuario;

@RequestScoped
@SuppressWarnings("unchecked")
public class UsuarioDAO extends BaseDAO<Usuario> {
	
	public Usuario buscaUsuarioPorFacebookUserId(String facebookUserId) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder queryBuilder = new StringBuilder("select u from Usuario u ");
		queryBuilder.append(" where u.excluido <> true ");
		queryBuilder.append(" and u.facebookUserId = :facebookUserId ");
		parameters.put("facebookUserId", facebookUserId);

		// seta os parâmetros na query
		Query query = em.createQuery(queryBuilder.toString());
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario findByLoginESenha(String login, String password) {
		try {
			return (Usuario) em.createNamedQuery("findUsuarioByLoginESenha").setParameter("login", login)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario findByLogin(String login) {
		try {
			return (Usuario) em.createNamedQuery("findUsuarioByLogin").setParameter("login", login.toLowerCase()).getSingleResult();
		} catch (NoResultException noex) {
			return null;
		}
	}

	public List<Usuario> findAllUsuarios() {
		return em.createNamedQuery("findAllUsuarios").getResultList();
	}

	public List<Modulo> getModulos(Usuario usuarioLogado) {
		return em.createNamedQuery("findModulosByUsuario").setParameter("u", usuarioLogado).getResultList();
	}

	public List<Funcionalidade> getFuncionalidades(Usuario usuarioLogado, Modulo moduloSelecionado) {
		return em.createNamedQuery("findFuncionalidadesByUsuarioAndModulo").setParameter("u", usuarioLogado)
				.setParameter("m", moduloSelecionado).getResultList();

	}

	public List<Usuario> pesquisarUsuarios(FilterUsuarioDTO filter) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (filter == null) {
			filter = new FilterUsuarioDTO();
		}

		StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT u FROM Usuario u ");
		queryBuilder.append(" WHERE u.excluido <> true ");

		if (filter.getNome() != null) {
			queryBuilder.append(" and lower(u.nome) like :nome ");
			parameters.put("nome", "%" + filter.getNome().toLowerCase().concat("%"));
		}

		if (filter.getLogin() != null) {
			queryBuilder.append(" and lower(u.login) like :login ");
			parameters.put("login", "%" + filter.getLogin().toLowerCase().concat("%"));
		}

		if (filter.getDataAtualizacao() != null) {
			queryBuilder.append(" and u.dataAlteracao > :data ");
			parameters.put("data", filter.getDataAtualizacao());
		}

		

		Query query = em.createQuery(queryBuilder.toString());

		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		return query.getResultList();
	}

	public Usuario pesquisarUsuariosPorEmail(String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder queryBuilder = new StringBuilder("select u from Usuario u ");
		queryBuilder.append(" where u.excluido <> true ");
		queryBuilder.append(" and lower(u.email) = :email ");
		parameters.put("email", email.toLowerCase());

		// seta os parâmetros na query
		Query query = em.createQuery(queryBuilder.toString());
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Usuario> limaparTokens(Usuario usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder queryBuilder = new StringBuilder("select u from Usuario u ");
		queryBuilder.append(" where lower(u.deviceToken) = :deviceToken ");
		parameters.put("deviceToken", usuario.getDeviceToken().toLowerCase());

		// seta os parâmetros na query
		Query query = em.createQuery(queryBuilder.toString());
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		try {
			return  query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Usuario pesquisarUsuariosPorCPF(String cpf) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		StringBuilder queryBuilder = new StringBuilder("select u from Usuario u ");
		queryBuilder.append(" where u.excluido <> true ");
		queryBuilder.append(" and lower(u.cpf) = :cpf ");
		parameters.put("cpf", cpf.toLowerCase());

		// Seta os parâmetros na query
		Query query = em.createQuery(queryBuilder.toString());
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}

		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
