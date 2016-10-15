package com.starksoftware.library.abstracts.model.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum ChavePushEnum {

	APP_FILA_ANDROID("AIzaSyCs96Kq_8QIkEaiRy9CtactzHGVGxIDgog"),
	APP_ARGOS_ANDROID("AIzaSyDZbFcB6FuqAgDrPKs7YbJaGP8iodCoLkw"),
	APP_ARGOS_IOS_DEV("-"),
	APP_ARGOS_IOS_PROD("-");

	private String chave;

	ChavePushEnum(String chave) {
		this.chave = chave;
	}

	@Override
	public String toString() {
		return chave;
	}

	public static List<ChavePushEnum> getValuesList() {
		List<ChavePushEnum> valores = new ArrayList<ChavePushEnum>();

		for (ChavePushEnum tipo : values()) {
			valores.add(tipo);
		}
		return valores;
	}

	public boolean equals(ChavePushEnum chavePush) {
		if (chavePush != null && chavePush.getChave() != null && this != null && this.chave != null) {
			if (chavePush.getChave().equals(this.chave))
				return true;
		}
		return false;

	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}
}
