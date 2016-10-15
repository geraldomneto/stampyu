package com.starksoftware.library.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.library.security.model.entity.TipoUsuario;

@JsonInclude(Include.NON_NULL)
public class TipoUsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = 1782603448357306900L;

	public TipoUsuarioDTO() {
	}

	public TipoUsuarioDTO(TipoUsuario tipoUsuario) {
		descricao = tipoUsuario.getDescricao();
		id = tipoUsuario.getId();
	}

	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
}
