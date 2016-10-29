package com.starksoftware.library.security.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.LazyInitializationException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.starksoftware.library.abstracts.dto.BaseDTO;
import com.starksoftware.library.security.model.entity.Perfil;
import com.starksoftware.library.security.model.entity.TipoUsuarioEnum;
import com.starksoftware.library.security.model.enumeration.RoleAccess;
import com.starksoftware.stampyu.model.entity.Usuario;

@JsonInclude(Include.NON_NULL)
public class UsuarioDTO extends BaseDTO {

	private static final long serialVersionUID = 6496226282189066109L;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Usuario usuario) {
		if (usuario != null) {
			setId(usuario.getId());
			setNome(usuario.getNome());
			setEmail(usuario.getEmail());
			setLogin(usuario.getLogin());
			setAtivo(usuario.isAtivo());
			setSenha(usuario.getSenha());

			if (usuario.getTipoUsuario() != null) {
				setTipoUsuario(new TipoUsuarioDTO(usuario.getTipoUsuario()));
			}
			
			try{
				setPerfis(new ArrayList<PerfilDTO>());
				for (Perfil perfil : usuario.getPerfis()) {
					getPerfis().add(new PerfilDTO(perfil));
				}
			}catch(LazyInitializationException la){
				System.err.println(la.getMessage());
			}
			
		}
	}

	private String nome;
	private String email;
	private String login;
	private String senha;
	private String codigo;
	private Long representanteId;
	private Long dataValidadeLimite;

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}

	private String ramal;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	private Boolean ativo;

	private TipoUsuarioDTO tipoUsuario;

	private List<PerfilDTO> perfis;

	private String token;

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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public TipoUsuarioDTO getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<PerfilDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<PerfilDTO> perfis) {
		this.perfis = perfis;
	}

	public RoleAccess getRoleAccess() {

		if (getId() != null) {
			if (getTipoUsuario() != null
					&& TipoUsuarioEnum.ADMINISTRADOR.getId().equals(getTipoUsuario().getId().intValue())) {

				return RoleAccess.ADMIN;
			}
			return RoleAccess.USER;
		}

		return null;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getRepresentanteId() {
		return representanteId;
	}

	public void setRepresentanteId(Long representanteId) {
		this.representanteId = representanteId;
	}

	public Long getDataValidadeLimite() {
		return dataValidadeLimite;
	}

	public void setDataValidadeLimite(Long dataValidadeLimite) {
		this.dataValidadeLimite = dataValidadeLimite;
	}
}
