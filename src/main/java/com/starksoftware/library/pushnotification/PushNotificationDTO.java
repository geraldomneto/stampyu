package com.starksoftware.library.pushnotification;

import java.io.Serializable;
import java.util.List;

public class PushNotificationDTO implements Serializable {

	private static final long serialVersionUID = -5789440315618902637L;

	private Long userID;

	private String deviceToken;
	private String plataforma;
	private String tipo;
	private String chavePush;

	private List<PushPayloadDTO> listaPayload;

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public List<PushPayloadDTO> getListaPayload() {
		return listaPayload;
	}

	public void setListaPayload(List<PushPayloadDTO> listaPayload) {
		this.listaPayload = listaPayload;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {

		StringBuilder toString = new StringBuilder();
		toString.append("##### PushNotificationDTO #### -  deviceToken = ").append(deviceToken);
		toString.append("-  plataforma = ").append(plataforma);

		for (PushPayloadDTO dto : listaPayload) {
			toString.append(" key = ").append(dto.getKey()).append(" Valor = ").append(dto.getValor());
		}

		return toString.toString();
	}

	public String getChavePush() {
		return chavePush;
	}

	public void setChavePush(String chavePush) {
		this.chavePush = chavePush;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

}