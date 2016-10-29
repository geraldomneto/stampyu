package com.starksoftware.stampyu.model.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.starksoftware.library.abstracts.model.business.AbstractFacade;
import com.starksoftware.stampyu.model.dao.NivelExperienciaPomboDAO;
import com.starksoftware.stampyu.model.entity.NivelExperienciaPombo;

@Stateless
public class NivelExperienciaPomboFacade extends AbstractFacade {
	
	@Inject
	private NivelExperienciaPomboDAO nivelExperienciaPomboDAO;
	
	public List<NivelExperienciaPombo> findAll() {
		return nivelExperienciaPomboDAO.findAll();
	}
	
	public NivelExperienciaPombo findByPrimaryKey(Long id) {
		return nivelExperienciaPomboDAO.findByPrimaryKey(id);
	}
	
	public NivelExperienciaPombo saveOrUpdate(NivelExperienciaPombo nivelExperienciaPombo) {
		return nivelExperienciaPomboDAO.saveOrUpdate(nivelExperienciaPombo);
	}

	public void delete(NivelExperienciaPombo nivelExperienciaPombo) {
		nivelExperienciaPombo.setExcluido(true);
		nivelExperienciaPomboDAO.saveOrUpdate(nivelExperienciaPombo);
	}
}
