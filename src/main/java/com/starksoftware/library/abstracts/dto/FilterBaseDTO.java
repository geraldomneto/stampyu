package com.starksoftware.library.abstracts.dto;

import java.util.Date;

import com.starksoftware.stampyu.model.entity.Usuario;

public class FilterBaseDTO implements IDTO {

	protected String numeroEntrega;
	protected Usuario usuario;
	protected Date dataPrevisao;
	protected int limit = 200;
	protected int offset = 0;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataPrevisao() {
		return dataPrevisao;
	}

	public void setDataPrevisao(Date dataPrevisao) {
		this.dataPrevisao = dataPrevisao;
	}

	public String getNumeroEntrega() {
		return numeroEntrega;
	}

	public void setNumeroEntrega(String numeroEntrega) {
		this.numeroEntrega = numeroEntrega;
	}
}
