package com.starksoftware.stampyu.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "t_carta", schema = "public")
public class Carta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CARTA")
	@SequenceGenerator(name = "SQ_CARTA", sequenceName = "SQ_CARTA", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;
	
	private Usuario remetende;
	private Usuario destinatario;
	private List<SeloCarta> selos;
	private String mensagem;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getRemetende() {
		return remetende;
	}
	public void setRemetende(Usuario remetende) {
		this.remetende = remetende;
	}
	public Usuario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}
	public List<SeloCarta> getSelos() {
		return selos;
	}
	public void setSelos(List<SeloCarta> selos) {
		this.selos = selos;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
