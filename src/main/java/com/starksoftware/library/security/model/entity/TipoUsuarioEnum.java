package com.starksoftware.library.security.model.entity;

/**
 * 
 * @author miqueias.gomes
 * @see ...main\webapp\WEB-INF\classes\import.sql
 *
 */
public enum TipoUsuarioEnum {

	ADMINISTRADOR(1l, "Administrador"),
	USUARIO_FACEBOOK(2l, "Usuário do Facebook");

	private Long id;
	private String description;

	private TipoUsuarioEnum(long id, String description) {
		this.id = id;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
