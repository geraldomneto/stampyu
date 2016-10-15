package com.starksoftware.stampyu.dto;

import java.util.Base64;

import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.stampyu.model.entity.Selo;

public class SeloDTO extends BaseDTO {

	private Long id;
	private Integer numeroSerie;
	private String titulo;
	private String imagem;
	private String backgroundColorHexa;
	
	public SeloDTO() {}
	
	public SeloDTO(Selo selo) {
		this.id = selo.getId();
		this.numeroSerie = selo.getNumeroSerie();
		this.titulo  = selo.getTitulo();
		if (selo.getImagem() != null) {
			try {
				this.imagem = Base64.getEncoder().encodeToString(selo.getImagem());							
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.backgroundColorHexa = selo.getBackgroundColorHexa();
	}

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getBackgroundColorHexa() {
		return backgroundColorHexa;
	}

	public void setBackgroundColorHexa(String backgroundColorHexa) {
		this.backgroundColorHexa = backgroundColorHexa;
	}
}