package com.starksoftware.stampyu.dto;

import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.stampyu.model.entity.NivelExperienciaPombo;

public class NivelExperienciaPomboDTO extends BaseDTO {

	private Long id;
	private String descricao;
	private Long velocidadeKmPorHora;
	
	public NivelExperienciaPomboDTO() {}
	
	public NivelExperienciaPomboDTO(NivelExperienciaPombo nivelExperienciaPombo) {
		this.id = nivelExperienciaPombo.getId();
		this.descricao = nivelExperienciaPombo.getDescricao();
		this.velocidadeKmPorHora = nivelExperienciaPombo.getVelocidadeKmPorHora();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getVelocidadeKmPorHora() {
		return velocidadeKmPorHora;
	}

	public void setVelocidadeKmPorHora(Long velocidadeKmPorHora) {
		this.velocidadeKmPorHora = velocidadeKmPorHora;
	}
}
