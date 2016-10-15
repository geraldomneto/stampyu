package com.starksoftware.stampyu.dto;

import com.starksoftware.library.abstracts.dto.BaseDTO;

public class UsuarioDTO extends BaseDTO {

	private String login;
	private String nome;
	private String email;
	private String deviceToken;
	private String deviceType;
	private Long tipoUsuario;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Long getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(Long tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
