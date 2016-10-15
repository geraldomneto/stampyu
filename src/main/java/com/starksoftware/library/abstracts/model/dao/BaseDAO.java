package com.starksoftware.library.abstracts.model.dao;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;
import com.starksoftware.library.abstracts.model.entity.IEntidade;
import com.starksoftware.library.abstracts.model.exception.ForeignKeyViolationException;
import com.starksoftware.library.abstracts.model.exception.ObjetoObsoletoException;

/**
 * 
 * @author miqueias.gomes
 *
 * @param <T>
 * 
 */
@SuppressWarnings("all")
public class BaseDAO<T extends EntidadeDominioBase> {

	protected Logger logger = Logger.getLogger(this.getClass().getName());

	private static final String FOREIGN_KEY_VIOLATION_SQLSTATE = "23503";

	@PersistenceContext
	protected EntityManager em;

	/**
	 * @param key
	 * @return
	 */
	public T findByPrimaryKey(Long key) {
		return (T) em.find(getEntityClass(), key);
	}

	/**
	 * @param key
	 * @param init
	 * @return
	 */
	public T load(Long key, String... init) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(getEntityClass());
		Root<T> emp = cq.from(getEntityClass());

		for (String i : init) {
			emp.fetch(i);
		}
		cq.where(cb.equal(emp.get("id"), key));
		cq.select(emp);
		TypedQuery<T> query = em.createQuery(cq);

		return (T) query.getSingleResult();
	}

	/**
	 * @return
	 */
	public List<T> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(getEntityClass());

		Root<T> emp = cq.from(getEntityClass());
		cq.select(emp);

		addNotDeletedConstraint(cb, cq, emp);
		addOrderById(cb, cq, emp);

		TypedQuery<T> query = em.createQuery(cq);

		List<T> rows = query.getResultList();

		return rows;
	}

	/**
	 * @param cb
	 * @param cq
	 * @param emp
	 * @return
	 */
	protected CriteriaQuery addNotDeletedConstraint(CriteriaBuilder cb, CriteriaQuery cq, Root<T> emp) {

		Class classe = getEntityClass();
		CriteriaQuery query = cq;

		try {
			Method method = classe.getSuperclass().getMethod("isExcluido", null);
			query = cq.where(cb.equal(emp.get("excluido"), Boolean.FALSE));

		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		}
		return query;

	}

	/**
	 * @param cb
	 * @param cq
	 * @param emp
	 * @return
	 */
	protected CriteriaQuery addOrderById(CriteriaBuilder cb, CriteriaQuery cq, Root<T> emp) {

		Class classe = getEntityClass();
		CriteriaQuery query = cq;

		try {
			Method method = classe.getSuperclass().getMethod("getId", null);
			query = cq.orderBy(cb.desc(emp.get("id")));

		} catch (NoSuchMethodException ex) {
		}

		return query;

	}

	/**
	 * @param object
	 * @return
	 */
	public T saveOrUpdate(T object) {

		try {
			if (object.getId() != null) {
				object.setDataAlteracao(new Date());
				object = em.merge(object);
			} else {
				object.setDataInsercao(new Date());
				object.setDataAlteracao(new Date());
				em.persist(object);
			}

		} catch (OptimisticLockException ole) {
			throw new ObjetoObsoletoException(
					"O objeto [" + object + "] não pode ser gravado pois foi atualizado após sua última consulta.",
					object);
		} catch (Exception ex) {
			Throwable t = ex;
			while (t != null && !(t instanceof ConstraintViolationException))
				t = t.getCause();

			if (t == null)
				throw (RuntimeException) ex;
			ConstraintViolationException e = (ConstraintViolationException) t;

			logger.severe("CONSTRAINT VIOLATIONS SAVING : " + object.getClass().getName());

			for (ConstraintViolation v : e.getConstraintViolations()) {
				StringBuilder str = new StringBuilder();
				str.append("CONSTRAINT: Classe= ").append(v.getRootBeanClass().getName().toUpperCase()).append(" - ")
						.append(v.getMessage().toUpperCase()).append(" / FIELD = ")
						.append(v.getPropertyPath().toString().toUpperCase());
				logger.severe(str.toString());
				logger.severe(v.getLeafBean().getClass() + "." + v.getPropertyPath() + ":"
						+ v.getConstraintDescriptor().toString());
			}

			throw (RuntimeException) ex;
		}
		return object;
	}

	/**
	 * @param object
	 */
	public void delete(T object) {
		try {
			em.remove(em.merge(object));
			em.flush();
		} catch (OptimisticLockException ole) {
			throw new ObjetoObsoletoException(
					"O objeto [" + object + "] nÃ£o pode ser gravado pois foi atualizado apÃ³s sua Ãºltima consulta.",
					object);
		} catch (RuntimeException ex) {
			Throwable root = ExceptionUtils.getRootCause(ex);
			if (root == null)
				root = ex;

			if (root instanceof SQLException) {
				String SQLState = ((SQLException) root).getSQLState();

				if (SQLState != null && SQLState.equals(FOREIGN_KEY_VIOLATION_SQLSTATE)) {
					logger.severe("CONSTRAINT VIOLATIONS DELETING : " + object.getClass().getName());
					throw new ForeignKeyViolationException("exception.foreign.key.violation", object.getClass());
				}
			}
			throw ex;
		}
	}

	/**
	 * @return
	 */
	private Class getEntityClass() {
		ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class) t.getActualTypeArguments()[0];
	}

	/**
	 * @param entity
	 * @return
	 */
	public T merge(T entity) {
		try {
			return em.merge(entity);
		} catch (OptimisticLockException ole) {
			throw new ObjetoObsoletoException(
					"O objeto [" + entity + "] nÃ£o pode ser gravado pois foi atualizado apÃ³s sua Ãºltima consulta.",
					entity);
		}
	}

	/**
	 * @param query
	 * @param parameters
	 */
	public void setQueryParameters(Query query, Map<String, Object> parameters) {
		for (String key : parameters.keySet()) {
			query.setParameter(key, parameters.get(key));
		}
	}

	/**
	 * @return
	 */
	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	/**
	 * @param flushType
	 */
	public void setFlushMode(FlushModeType flushType) {
		em.setFlushMode(flushType);
	}

	/**
	 * 
	 */
	public void flush() {
		try {
			em.flush();
		} catch (OptimisticLockException ole) {
			throw new ObjetoObsoletoException(
					"O objeto [" + ole.getEntity()
							+ "] nÃ£o pode ser gravado pois foi atualizado apÃ³s sua Ãºltima consulta.",
					(IEntidade) ole.getEntity());
		}
	}

	/**
	 * @param fieldOrder
	 * @return
	 */
	public List<T> findAllOrder(String fieldOrder) {
		StringBuilder sqlQuery = new StringBuilder().append(" SELECT DISTINCT obj FROM ")
				.append(getEntityClass().getName()).append(" AS obj WHERE obj.excluido <> true ")
				.append(" ORDER BY obj.").append(fieldOrder);

		return em.createQuery(sqlQuery.toString()).getResultList();
	}
}
