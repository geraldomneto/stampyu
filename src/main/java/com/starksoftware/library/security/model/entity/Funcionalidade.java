package com.starksoftware.library.security.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "t_funcionalidade", schema = "public")
@NamedQueries({ @NamedQuery(name = "findFuncionalidadesByUsuarioAndModulo", query = " select distinct f from Usuario u "
		+ " inner join u.perfis as p " + " inner join p.funcionalidades as f " + " inner join f.modulo as m "
		+ " where u=:u and f.modulo=:m  and f.excluido <> true " + " order by m.ordemExibicao, f.ordemExibicao "),

		@NamedQuery(name = "findFuncionalidadesByUsuarioAndModuloMenu", query = " select distinct f from Usuario u "
				+ " inner join u.perfis as p " + " inner join p.funcionalidades as f " + " inner join f.modulo as m "
				+ " where u=:u and f.modulo=:m and f.itemMenu = true and f.excluido <> true "
				+ " order by m.ordemExibicao, f.ordemExibicao"),

		@NamedQuery(name = "findFuncionalidadesByTipoUsuario", query = " select distinct f from Funcionalidade f "
				+ " where f.excluido <> true "
				+ " order by  f.descricao "),

		@NamedQuery(name = "findFuncionalidadesByTipoUsuarioEModulo", query = " select distinct f from Funcionalidade f "
				+ " where f.modulo.id = :modulo and f.excluido <> true "
				+ " order by f.descricao "),

		@NamedQuery(name = "findAllFuncionalidades", query = " select distinct f from Funcionalidade f "
				+ " join f.modulo as m " + " where f.excluido <> true " + " order by m.ordemExibicao, f.ordemExibicao")

})
public class Funcionalidade extends EntidadeDominioBase implements Comparable<Funcionalidade> {

	private static final long serialVersionUID = 6552309709697049759L;

	@Id
	@GeneratedValue(generator = "SQ_FUNCIONALIDADE")
	@SequenceGenerator(name = "SQ_FUNCIONALIDADE", sequenceName = "SQ_FUNCIONALIDADE", allocationSize = 1)
	protected Long id;

	@NotNull
	@Column(name = "descricao", nullable = false)
	private String descricao;

	
	@NotNull
	@Column(name = "ordem_exibicao", nullable = false)
	private Integer ordemExibicao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "fk_modulo", nullable = false)
	private Modulo modulo;

	@NotNull
	@Column(name = "descricao_resumida", nullable = false)
	private String descricaoResumida;

	@Column(name = "item_menu")
	private Boolean itemMenu;

	@NotNull
	@Column(name = "url", nullable = false)
	private String url;

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOrdemExibicao() {
		return ordemExibicao;
	}

	public void setOrdemExibicao(Integer ordemExibicao) {
		this.ordemExibicao = ordemExibicao;
	}

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	@Override
	public String toString() {
		return descricao;
	}

	public Boolean getItemMenu() {
		return itemMenu;
	}

	public void setItemMenu(Boolean itemMenu) {
		this.itemMenu = itemMenu;
	}

	@Override
	public int compareTo(Funcionalidade o) {
		return descricao.compareToIgnoreCase(o.getDescricao());
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
