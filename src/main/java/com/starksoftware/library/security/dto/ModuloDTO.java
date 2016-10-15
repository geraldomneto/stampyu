package com.starksoftware.library.security.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.library.security.model.entity.Modulo;

@JsonInclude(Include.NON_NULL)
public class ModuloDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	public ModuloDTO() {
	}

	public ModuloDTO(Modulo modulo) {
		setId(modulo.getId());
		setNome(modulo.getNome());
		setOrdemExibicao(modulo.getOrdemExibicao());
	}

	private String nome;
	private Integer ordemExibicao;
	private List<FuncionalidadeDTO> funcionalidades;

	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}

	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<FuncionalidadeDTO> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadeDTO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}
}
