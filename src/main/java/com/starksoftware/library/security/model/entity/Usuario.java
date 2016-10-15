
package com.starksoftware.library.security.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;
import com.starksoftware.library.security.model.enumeration.RoleAccess;

@Entity
@Table(name = "t_usuario", schema = "public")
@NamedQueries({ @NamedQuery(name = "findAllUsuarios", query = "select u from Usuario u where u.excluido <> true"),
		@NamedQuery(name = "findUsuarioByLoginESenha", query = "select u from Usuario u where upper(u.login) = upper(:login) and u.senha=:password and u.ativo=true and u.excluido<>true"),
		@NamedQuery(name = "findUsuarioByLogin", query = "select u from Usuario u where lower(u.login) = :login and u.excluido <> true ") })
public class Usuario extends EntidadeDominioBase {

	private static final long serialVersionUID = 9016395718242225342L;

	@Id
	@GeneratedValue(generator = "SQ_USUARIO")
	@SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "facebook_user_id")
	private String facebookUserId;

	@Column(name = "nome")
	private String nome;

	@Column(name = "email")
	private String email;

	@Column(name = "login")
	private String login;

	@Column(name = "senha")
	private String senha;

	@Column(name = "device_token")
	private String deviceToken;

	@Column(name = "device_type")
	private String deviceType;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_tipo_usuario")
	private TipoUsuario tipoUsuario;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "T_USUARIO_PERFIL")
	private List<Perfil> perfis = new ArrayList<Perfil>();

	@Transient
	private RoleAccess roleAccess;

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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public RoleAccess getRoleAccess() {

		if (getTipoUsuario() != null
				&& TipoUsuarioEnum.ADMINISTRADOR.getId().equals(getTipoUsuario().getId().intValue())) {

			return RoleAccess.ADMIN;
		}

		return RoleAccess.USER;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleAccess(RoleAccess roleAccess) {
		this.roleAccess = roleAccess;
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

	@Transient
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFacebookUserId() {
		return facebookUserId;
	}

	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
}
