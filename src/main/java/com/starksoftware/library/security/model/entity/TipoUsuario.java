package com.starksoftware.library.security.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "t_tipo_usuario", schema = "public")
public class TipoUsuario extends EntidadeDominioBase {

	private static final long serialVersionUID = -6052677967165633211L;
	
	public static final TipoUsuario ADMINISTRADOR = new TipoUsuario(1l);
	public static final TipoUsuario USUARIO = new TipoUsuario(2l);
	public static final TipoUsuario CLIENTE = new TipoUsuario(3l);

	public TipoUsuario() {
	}

	@Id
	@GeneratedValue(generator = "SQ_TIPO_USUARIO")
	@SequenceGenerator(name = "SQ_TIPO_USUARIO", sequenceName = "SQ_TIPO_USUARIO", allocationSize = 1)
	@Column(name = "id")
	protected Long id;

	public TipoUsuario(long id) {
		this.id = id;
	}

	@NotNull
	@Column(name = "Descricao", nullable = false)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	@Override
	public String toString() {
		return descricao;
	}

	public int compareTo(TipoUsuario o) {
		return descricao.compareToIgnoreCase(o.getDescricao());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
