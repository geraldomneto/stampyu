package com.starksoftware.stampyu.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;
import com.starksoftware.library.security.model.enumeration.SexoEnum;

@Entity
@Table(name = "t_pombo", schema = "public")
public class Pombo extends EntidadeDominioBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_POMBO")
	@SequenceGenerator(name = "SQ_POMBO", sequenceName = "SQ_POMBO", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Enumerated(EnumType.STRING)
	@Column(name = "sexo")
	private SexoEnum sexo;

	@Column(name = "kilometragem_percorrida")
	private Long kilometragemPercorrida;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_nivel_experiencia_pombo")
	private NivelExperienciaPombo nivelExperiencia;

	@ManyToOne
	@JoinColumn(name = "fk_entrega_atual")
	private Entrega entregaAtual;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public SexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(SexoEnum sexo) {
		this.sexo = sexo;
	}
	public Long getKilometragemPercorrida() {
		return kilometragemPercorrida;
	}
	public void setKilometragemPercorrida(Long kilometragemPercorrida) {
		this.kilometragemPercorrida = kilometragemPercorrida;
	}
	public NivelExperienciaPombo getNivelExperiencia() {
		return nivelExperiencia;
	}
	public void setNivelExperiencia(NivelExperienciaPombo nivelExperiencia) {
		this.nivelExperiencia = nivelExperiencia;
	}
	public Entrega getEntregaAtual() {
		return entregaAtual;
	}
	public void setEntregaAtual(Entrega entregaAtual) {
		this.entregaAtual = entregaAtual;
	}
}
