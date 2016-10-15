package com.starksoftware.library.pushnotification;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class PushAndroid {

	final String GCM_API_KEY = "AIzaSyB1xi-FEYIAMhWpgSvFy4ghnx4whl-Uh7g";
	final int retries = 3;

	private static PushAndroid instance;

	public static synchronized PushAndroid getInstance() {

		if (instance == null) {
			instance = new PushAndroid();
		}
		return instance;

	}

	public String sendPush(PushNotificationDTO pushDTO) {

		try {

			Sender sender = new Sender(GCM_API_KEY);
			Builder builder = new Builder();

			// tempo que a mensagem ficara no servidor para ser enviada caso o device esteja offline
			builder.timeToLive(3600);

			for (PushPayloadDTO payload : pushDTO.getListaPayload()) {
				builder.addData(payload.getKey(), payload.getValor());
			}

			if(pushDTO.getTipo() != null)
				builder.addData("tipo", pushDTO.getTipo());
			
			Message msg = builder.build();

			// momento de envio do push com a mensagem, token do aparelho  numero de tentativas
			Result result = sender.send(msg, pushDTO.getDeviceToken(), retries);

			if (result != null && result.getErrorCodeName() != null) {
				System.out.println("ERRO: " + result.getErrorCodeName());
				return "ERRO: " + result.getErrorCodeName();

			} else {
				return "Sucesso";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ERRO: Exception";

		}

	}

}
