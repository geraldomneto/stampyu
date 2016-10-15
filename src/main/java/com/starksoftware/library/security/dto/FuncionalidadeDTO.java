package com.starksoftware.library.security.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.library.security.model.entity.Funcionalidade;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class FuncionalidadeDTO extends BaseDTO {

	private static final long serialVersionUID = 1332270649416731742L;

	public FuncionalidadeDTO() {
	}

	public FuncionalidadeDTO(Funcionalidade funcionalidade) {
		setId(funcionalidade.getId());
		setDescricao(funcionalidade.getDescricao());
		setDescricaoResumida(funcionalidade.getDescricaoResumida());
		setUrl(funcionalidade.getUrl());
	}

	private String descricao;

	private Integer ordemExibicao;

	private List<PerfilDTO> perfis = new ArrayList<PerfilDTO>();

	private List<TipoUsuarioDTO> tiposUsuario = new ArrayList<TipoUsuarioDTO>();

	private ModuloDTO modulo;

	private String descricaoResumida;

	private Boolean itemMenu;

	private String url;

	private FuncionalidadeDTO funcionalidadePai;

	private List<FuncionalidadeDTO> funcionalidadesFilha;

	public ModuloDTO getModulo() {
		return modulo;
	}

	public void setModulo(ModuloDTO modulo) {
		this.modulo = modulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PerfilDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<PerfilDTO> perfis) {
		this.perfis = perfis;
	}

	public List<TipoUsuarioDTO> getTiposUsuario() {
		return tiposUsuario;
	}

	public void setTiposUsuario(List<TipoUsuarioDTO> tiposUsuario) {
		this.tiposUsuario = tiposUsuario;
	}

	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}

	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	@Override
	public String toString() {
		return descricao;
	}

	public Boolean getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(Boolean itemMenu) {
		this.itemMenu = itemMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public FuncionalidadeDTO getFuncionalidadePai() {
		return funcionalidadePai;
	}

	public void setFuncionalidadePai(FuncionalidadeDTO funcionalidadePai) {
		this.funcionalidadePai = funcionalidadePai;
	}

	public List<FuncionalidadeDTO> getFuncionalidadesFilha() {
		return funcionalidadesFilha;
	}

	public void setFuncionalidadesFilha(List<FuncionalidadeDTO> funcionalidadesFilha) {
		this.funcionalidadesFilha = funcionalidadesFilha;
	}

}
