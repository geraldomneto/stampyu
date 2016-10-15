package com.starksoftware.library.security.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "T_PERFIL", schema = "public")
@NamedQueries({
		@NamedQuery(name = "findPerfilByTipoUsuario", query = "select p from Perfil p where p.tipoUsuario=:tipo and p.excluido<>true"),
		@NamedQuery(name = "findPerfisUsuarioMaster", query = "select p from Perfil p where p.tipoUsuario.id not in (:administrador, :master) and p.excluido<>true") })
public class Perfil extends EntidadeDominioBase {

	
	private static final long serialVersionUID = -3020149070362607227L;
	public static final Long CLIENTE = 3l;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PERFIL")
	@SequenceGenerator(name = "SQ_PERFIL", sequenceName = "SQ_PERFIL", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	protected Long id;

	@NotNull
	@Column(name = "Nome", nullable = false)
	private String nome;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fk_tipo_usuario")
	private TipoUsuario tipoUsuario;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_PERFIL_FUNCIONALIDADE")
	@OrderBy("descricao ASC")
	private List<Funcionalidade> funcionalidades = new ArrayList<Funcionalidade>();

	
	public List<Funcionalidade> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<Funcionalidade> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
