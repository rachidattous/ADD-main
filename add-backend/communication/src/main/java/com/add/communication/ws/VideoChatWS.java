package com.add.communication.ws;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.add.communication.configuration.SpringContext;
import com.add.communication.session.VideoChatSessionStore;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint(value = "/api/communication/videoChat/{sessionId}")
public class VideoChatWS {

	private final VideoChatSessionStore videoChatSessionStore;

	public VideoChatWS() {

		this.videoChatSessionStore = SpringContext.getContext().getBean(VideoChatSessionStore.class);
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("sessionId") String sessionId) {

		videoChatSessionStore.addSession(sessionId, session);
		log.info("connected to session {} with current id {}", sessionId, session.getId());
	}

	@OnMessage
	public void onMessage(Session session, @PathParam("sessionId") String sessionId, String data) {

		send(sessionId, session, data);
	}

	@OnClose
	public void onClose(Session session, @PathParam("sessionId") String sessionId) {

		videoChatSessionStore.removeSession(sessionId, session);
		log.info("disconnected to session {} with current id {}", sessionId, session.getId());
	}

	@OnError
	public void onError(Session session, @PathParam("sessionId") String sessionId, Throwable throwable) {
		videoChatSessionStore.removeSession(sessionId, session);
		log.info("error in session {} with current id {}", sessionId,
				session.getId());
	}

	public void send(Session session, String data) {
		try {
			log.info("Sending data");
			session.getBasicRemote().sendText(data);
		} catch (Exception e) {
			log.error("WebSocket error, data was not sent. to session with id {}", session.getId());
		}
	}

	public void send(String sessionId, Session session, String data) {
		try {
			videoChatSessionStore.getAllSessionsExcept(sessionId, session).forEach(s -> send(s, data));
		} catch (Exception e) {
			log.error("WebSocket error, data was not sent.", e);
		}
	}

}
