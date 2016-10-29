package com.starksoftware.library.pushnotification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import com.starksoftware.library.abstracts.model.enumeration.ChavePushEnum;
import com.starksoftware.stampyu.model.entity.Usuario;

@Stateless
public class PushNotificationService {
	private Logger logger = Logger.getLogger(PushNotificationService.class.getName());

	@Resource(mappedName = "java:/jms/connectionfactory/PushNotificationEngine")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/jms/queue/PushNotificationEngine")
	private Queue queue;
	
	
	public void sendPushNotification(Usuario usuario, String mensagem, String titulo){
		PushNotificationDTO pushDTO = new PushNotificationDTO();

		pushDTO.setDeviceToken(usuario.getDeviceToken());
		pushDTO.setUserID(usuario.getId());
		pushDTO.setPlataforma(usuario.getDeviceType());

		if (pushDTO.getPlataforma().toLowerCase().equals("ios")) {

			pushDTO.setChavePush(ChavePushEnum.APP_ARGOS_IOS_PROD.toString());

		} else if (pushDTO.getPlataforma().toLowerCase().equals("android")) {

			pushDTO.setChavePush(ChavePushEnum.APP_FILA_ANDROID.toString());

		}

		List<PushPayloadDTO> listaPayload = new ArrayList<PushPayloadDTO>();

		listaPayload.add(new PushPayloadDTO("message", mensagem));
		listaPayload.add(new PushPayloadDTO("title", titulo));
		listaPayload.add(new PushPayloadDTO("idPush", usuario.getId().toString()));

		pushDTO.setListaPayload(listaPayload);

		sendPushNotification(pushDTO);

	}

	public void sendPushNotification(PushNotificationDTO pushDTO) {
		
		sendByJms(pushDTO);

	}

	private void sendByJms(Serializable object) {
		Connection conn = null;
		Session session = null;
		try {

			logger.finest("Criando conexao com factory: jms/connectionfactory/PushNotificationEngine");
			conn = connectionFactory.createConnection();

			logger.finest("Criando sessao auto-acknowledge");
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

			logger.finest("Criando produtor de mensagem");
			MessageProducer producer = session.createProducer(queue);

			logger.finest("Criando mensagem");
			ObjectMessage message = session.createObjectMessage(object);

			logger.finest("Enviando mensagem");
			producer.send(message);

			logger.finest("Fechando conexao");
			conn.close();

			session.close();
			logger.log(Level.FINE, "Mandou mensagem: {0}", object);

		} catch (JMSException e) {

			logger.log(Level.SEVERE, "Erro ao enviar mensagem JMS.", e);
			e.printStackTrace();

			if (conn != null && session != null)
				try {
					conn.close();
					session.close();
				} catch (Exception e1) {
					logger.finest("Erro Fechando Connection JMS - Catch");
				}

		} finally {

			if (conn != null && session != null) {
				try {
					conn.close();
					session.close();
				} catch (Exception e1) {
					logger.finest("Erro Fechando Connection JMS - Finally");
				}
			}
		}
	}
}
