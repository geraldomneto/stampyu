package com.starksoftware.library.pushnotification;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.ResponsePacket;

public class PushiOS {

	private Logger logger = Logger.getLogger(getClass().getName());

	private static String CERT_LOCATION_DEV = "pushcert.p12";
	private static String CERT_LOCATION_PROD = "iphone_prod.p12";

	private static String CERT_PASSWORD = "Araujo@push#Dev";

	private static boolean PRODUCAO = false;

	private static PushiOS instance;

	public static synchronized PushiOS getInstance() {

		if (instance == null) {
			instance = new PushiOS();

		}

		return instance;

	}

	public String sendPush(PushNotificationDTO pushDTO) {

		System.err.println("Enviando Push iOS " + pushDTO.getListaPayload().size());
		
		PushNotificationPayload payload = createPayloadPush(pushDTO);
		
		try {

			InputStream certificadoP12 = null;

			if (PRODUCAO == true) {

				logger.log(Level.INFO, "Push_iOS - PRUDUCAO ");
				certificadoP12 = PushiOS.class.getResourceAsStream(CERT_LOCATION_PROD);

			} else {

				logger.log(Level.INFO, "Push_iOS - DESENVOLVIMENTO");
				certificadoP12 = PushiOS.class.getResourceAsStream(CERT_LOCATION_DEV);
			}

			// File diretorioCertificado = new File(CERT_LOCATION_BASE);
			// if (!diretorioCertificado.exists()) {
			// logger.log(Level.WARNING, "Push_iOS - DIRETORIO INVALIDO");
			//
			// }
			// certificadoP12 = new FileInputStream(diretorioCertificado);

			
			
			System.err.println("Device TOKEN " + pushDTO.getDeviceToken() );
			System.err.println("Certificado " + certificadoP12 != null);
			List<PushedNotification> notifications = Push.payload(payload, certificadoP12, CERT_PASSWORD, PRODUCAO, pushDTO.getDeviceToken());

			for (PushedNotification notification : notifications) {

				if (notification.isSuccessful()) {

					logger.log(Level.INFO, "Push_iOS - Push Enviado");
					return "Sucesso";

				} else {

					Exception THEPROBLEM = notification.getException();
					THEPROBLEM.printStackTrace();

					ResponsePacket THEERRORRESPONSE = notification.getResponse();
					if (THEERRORRESPONSE != null) {

						logger.log(Level.WARNING, "Push_iOS Erro - " + THEERRORRESPONSE.getMessage());
						return "ERRO: " + THEERRORRESPONSE.getMessage();

					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return "ERRO: Exception";
		}

		return "ERRO: Desconhecido";
	}

	private PushNotificationPayload createPayloadPush(PushNotificationDTO pushDTO) {

		try {

			List<PushPayloadDTO> listaPayload = pushDTO.getListaPayload();
			
			PushNotificationPayload payload = PushNotificationPayload.complex();

			for (PushPayloadDTO temp : listaPayload) {
				System.err.println("Key = " + temp.getKey() + "/ Valor = " +  temp.getValor());
				payload.addCustomDictionary(temp.getKey(), temp.getValor());
				if(temp.getKey().equals("title")){
					payload.addAlert(temp.getValor());
				}
			}

			
			payload.addCustomDictionary("tipo", pushDTO.getTipo());
			
			//{"aps":{"alert":{"body":"Lorem ipsum dolor sit amet","title":"Nova mensagem"},"badge":1,"sound":"default"}}
			
			 payload.addBadge(1);
			 
			 
			// payload.addSound("default");
			 payload.addCustomDictionary("id", pushDTO.getUserID());

			return payload;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
