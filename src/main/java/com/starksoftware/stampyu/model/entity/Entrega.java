package com.starksoftware.stampyu.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "t_entrega", schema = "public")
public class Entrega extends EntidadeDominioBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENTREGA")
	@SequenceGenerator(name = "SQ_ENTREGA", sequenceName = "SQ_ENTREGA", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_carta")
	private Carta carta;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_pombo")
	private Pombo pombo;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_endereco_remetente")
	private Endereco enderecoRemetente;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "fk_endereco_destinatario")
	private Endereco enderecoDestinatario;
	
	@Column(name = "concluida")
	private boolean concluida = false;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Carta getCarta() {
		return carta;
	}
	public void setCarta(Carta carta) {
		this.carta = carta;
	}
	public Pombo getPombo() {
		return pombo;
	}
	public void setPombo(Pombo pombo) {
		this.pombo = pombo;
	}
	public Endereco getEnderecoRemetente() {
		return enderecoRemetente;
	}
	public void setEnderecoRemetente(Endereco enderecoRemetente) {
		this.enderecoRemetente = enderecoRemetente;
	}
	public Endereco getEnderecoDestinatario() {
		return enderecoDestinatario;
	}
	public void setEnderecoDestinatario(Endereco enderecoDestinatario) {
		this.enderecoDestinatario = enderecoDestinatario;
	}
	public boolean isConcluida() {
		return concluida;
	}
	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}
}
