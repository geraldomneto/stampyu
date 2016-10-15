package com.starksoftware.library.abstracts.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class EntidadeDominioBase extends EntidadeBase {

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao")
	private Date dataAlteracao;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_insercao")
	private Date dataInsercao;

	@JsonIgnore
	@Column(name = "usuario")
	private String usuarioAlteracao;

	@Column(name = "importado")
	private Boolean importado = Boolean.FALSE;

	@JsonIgnore
	@Column(name = "excluido")
	private Boolean excluido = Boolean.FALSE;

	@Column(name = "ativo")
	private Boolean ativo = Boolean.TRUE;


	@JsonIgnore
	@Transient
	private String search = null;
	
	public Boolean isAtivo() {

		if (ativo == null) {
			ativo = Boolean.FALSE;
		}
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuario) {
		this.usuarioAlteracao = usuario;
	}

	public Boolean isExcluido() {

		if (excluido == null) {
			excluido = Boolean.FALSE;
		}

		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	public Boolean getImportado() {
		return importado;
	}

	public Boolean isImportado() {
		return importado;
	}

	public void setImportado(Boolean importado) {
		this.importado = importado;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	
}
