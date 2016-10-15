package com.starksoftware.library.security.dto;

import java.io.Serializable;
import java.util.Date;

import com.starksoftware.library.abstracts.dto.FilterBaseDTO;
import com.starksoftware.library.security.model.entity.TipoUsuario;

public class FilterUsuarioDTO extends FilterBaseDTO implements Serializable {

	private static final long serialVersionUID = -5931272545219647747L;

	private String nome;
	private String login;
	private Date dataAtualizacao;
	private TipoUsuario tipoUsuarioLogado;
	private Boolean isAdmin;

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public TipoUsuario getTipoUsuarioLogado() {
		return tipoUsuarioLogado;
	}

	public void setTipoUsuarioLogado(TipoUsuario tipoUsuarioLogado) {
		this.tipoUsuarioLogado = tipoUsuarioLogado;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
