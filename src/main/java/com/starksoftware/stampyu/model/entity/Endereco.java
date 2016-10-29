package com.starksoftware.stampyu.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.starksoftware.library.abstracts.model.entity.EntidadeDominioBase;

@Entity
@Table(name = "t_endereco", schema = "public")
public class Endereco extends EntidadeDominioBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENDERECO")
	@SequenceGenerator(name = "SQ_ENDERECO", sequenceName = "SQ_ENDERECO", allocationSize = 1, schema = "public")
	@Column(name = "id", unique = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
