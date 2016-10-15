package com.starksoftware.library.abstracts.model.enumeration;

public enum ResponseCodesEnum {

	SUCCESS(204, "Sucesso"),
	BAD_REQUEST(400, "Requisição inválida"),
	UNAUTHORIZED(401, "Não autorizado"),
	FORBIDDEN(403, "Acesso proibido"),
	NOT_FOUND(404, "Não encontrado"),
	INTERNAL_SERVER_ERROR(500, "Erro ao processar sua requisição"),

	LOGIN_SENHA_INVALIDO(1000,"Login e/ou senha inválidos"),
	EMAIL_BLOQUEADO(1001, "E-mail bloqueado pela administração"),
	ACESSO_BLOQUEADO(1002, "Acesso bloqueado pela administração"),
	TOKEN_INVALIDO(1003, "Token de acesso inválido ou expirado"),
	ACESSO_NAO_PERMITIDO(1004, "Usuário não possui o nível de acesso necessário");

	private int id;
	private String description;

	private ResponseCodesEnum(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
