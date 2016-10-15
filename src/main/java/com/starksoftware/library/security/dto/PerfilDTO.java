package com.starksoftware.library.security.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.library.security.model.entity.Funcionalidade;
import com.starksoftware.library.security.model.entity.Perfil;

@JsonInclude(Include.NON_NULL)
public class PerfilDTO extends BaseDTO {

	private static final long serialVersionUID = 1323924955372511359L;

	public PerfilDTO() {
	}

	public PerfilDTO(Perfil perfil) {
		setId(perfil.getId());
		setNome(perfil.getNome());
		setTipoUsuario(new TipoUsuarioDTO(perfil.getTipoUsuario()));
		setFuncionalidades(perfil.getFuncionalidades());
	}

	private String nome;
	private TipoUsuarioDTO tipoUsuario;

	
	private List<Funcionalidade> funcionalidades = new ArrayList<Funcionalidade>();

	@JsonIgnore
	private List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public TipoUsuarioDTO getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}
}
