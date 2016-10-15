package com.starksoftware.library.pushnotification;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(	mappedName = "jms/queue/PushNotificationEngine", activationConfig = { 
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), 
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/PushNotificationEngine") 
	})
public class PushNotificationListener implements MessageListener {

	private Logger logger = Logger.getLogger(PushNotificationListener.class.getName());

	@Resource
	private MessageDrivenContext context;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {

				System.err.println("RECEBEU MENSAGEM NA FILA " );
				ObjectMessage m = (ObjectMessage) message;
				Serializable object = m.getObject();

				if (object instanceof PushNotificationDTO) {

					PushNotificationDTO pushDTO = (PushNotificationDTO) object;
					enviaPushNotification(pushDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, "Erro ao tratar mensagem JMS. " + e.getMessage(), e);
			}
		} else {
			System.err.println("MENSAGEM NAO instanceof ObjectMessage");
		}
	}

	public void enviaPushNotification(PushNotificationDTO pushDTO) {

		try{
			String retorno = "";

			System.err.println(String.format("Enviando Push para %s , deviceID: %s , Tipo= %s .",pushDTO.getPlataforma(),pushDTO.getDeviceToken(), pushDTO.getTipo()));
			
			if (pushDTO.getPlataforma().toLowerCase().equals("ios")) {
	
				retorno = PushiOS.getInstance().sendPush(pushDTO);
	
			} else if (pushDTO.getPlataforma().toLowerCase().equals("android")) {
	
				retorno = PushAndroid.getInstance().sendPush(pushDTO);
	
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		

//		if (retorno.contains("ERRO") && pushDTO.getUserID() != null) {
//
//			Usuario user = usuarioFacade.findByID(pushDTO.getUserID());
//			user.setSistemaOperacionalMobile(null);
//			user.setDeviceToken(null);
//			usuarioFacade.salvarUsuario(user);
//
//		}
//
//		if (pushDTO.getHistoricoPushID() != null) {
//
//			HistoricoPushEnviado historico = historicoPushEnviadoFacade.findById(pushDTO.getHistoricoPushID());
//			historico.setStatusEnvio(retorno);
//			historicoPushEnviadoFacade.salvarMensagem(historico);
//
//		}
	}

}
