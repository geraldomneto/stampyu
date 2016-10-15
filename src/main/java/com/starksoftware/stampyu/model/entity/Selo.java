package com.starksoftware.stampyu.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "t_selo", schema = "public")
public class Selo extends EntidadeDominioBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SELO")
	@SequenceGenerator(name = "SQ_SELO", sequenceName = "SQ_SELO", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;
	
	@Column(name = "numero_serie")
	private Integer numeroSerie;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "imagem")
	private byte[] imagem;
	
	@Column(name = "background_color_hexa")
	private String backgroundColorHexa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(Integer numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getBackgroundColorHexa() {
		return backgroundColorHexa;
	}

	public void setBackgroundColorHexa(String backgroundColorHexa) {
		this.backgroundColorHexa = backgroundColorHexa;
	}
}