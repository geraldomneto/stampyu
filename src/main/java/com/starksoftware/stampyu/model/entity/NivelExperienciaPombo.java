package com.starksoftware.stampyu.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;
import com.starksoftware.stampyu.dto.NivelExperienciaPomboDTO;

@Entity
@Table(name = "t_nivel_experiencia_pombo", schema = "public")
public class NivelExperienciaPombo extends EntidadeDominioBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_NIVEL_EXPERIENCIA_POMBO")
	@SequenceGenerator(name = "SQ_NIVEL_EXPERIENCIA_POMBO", sequenceName = "SQ_NIVEL_EXPERIENCIA_POMBO", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "velocidade_km_por_hora")
	private Long velocidadeKmPorHora;
	
	public NivelExperienciaPombo() {}
	
	public NivelExperienciaPombo(NivelExperienciaPomboDTO dto) {
		this.id = dto.getId();
		this.descricao = dto.getDescricao();
		this.velocidadeKmPorHora = dto.getVelocidadeKmPorHora();
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
