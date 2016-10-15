package com.starksoftware.library.pushnotification;

import java.io.Serializable;

public class PushPayloadDTO implements Serializable {

	private static final long serialVersionUID = 4209011015220598622L;

	private String key;
	private String valor;

	public PushPayloadDTO(String key, String valor) {
		this.key = key;
		this.valor = valor;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}