package com.starksoftware.library.security.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "T_MODULO", schema = "public")
@NamedQueries({
		@NamedQuery(name = "findModulosByUsuario", query = "select distinct f.modulo from Usuario as u inner join u.perfis as  p inner join p.funcionalidades as f inner join f.modulo as m where u=:u order by m.ordemExibicao") })
public class Modulo extends EntidadeDominioBase implements Comparable<Modulo> {

	private static final long serialVersionUID = 7786737275221518476L;

	@Id
	@GeneratedValue(generator = "SQ_MODULO")
	@SequenceGenerator(name = "SQ_MODULO", sequenceName = "SQ_MODULO", allocationSize = 1)
	@Column(name = "id")
	protected Long id;

	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "icone")
	private String icone;

	@NotNull
	@Column(name = "ordem_exibicao", nullable = false)
	private Integer ordemExibicao;

	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}

	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int compareTo(Modulo o) {
		return nome.compareToIgnoreCase(o.getNome());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
