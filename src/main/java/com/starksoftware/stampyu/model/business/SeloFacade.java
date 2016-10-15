package com.starksoftware.stampyu.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.stampyu.model.dao.SeloDAO;
import com.starksoftware.stampyu.model.entity.Selo;

@Stateless
public class SeloFacade extends AbstractFacade {
	
	@Inject
	private SeloDAO seloDAO;
	
	public List<Selo> findAll() {
		return seloDAO.findAll();
	}
	
	public Selo findByPrimaryKey(Long id) {
		return seloDAO.findByPrimaryKey(id);
	}
	
	public Selo saveOrUpdate(Selo selo) {
		return seloDAO.saveOrUpdate(selo);
	}

	public void delete(Selo selo) {
		seloDAO.delete(selo);
	}
}
