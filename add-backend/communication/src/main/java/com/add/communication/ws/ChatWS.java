package com.add.communication.ws;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.add.communication.configuration.SpringContext;
import com.add.communication.dto.ChatMessageDTO;
import com.add.communication.model.ChatMessage;
import com.add.communication.services.ChatMessageService;
import com.add.communication.session.ChatSessionStore;
import com.add.communication.ws.decoder.MessageDecoder;
import com.add.communication.ws.encoder.MessageEncoder;

@Slf4j
@Component
@ServerEndpoint(value = "/api/communication/chat/{sessionId}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatWS {

    private final ChatSessionStore chatSessionStore;

    private final ChatMessageService chatMessageService;

    public ChatWS() {

        this.chatSessionStore = SpringContext.getContext().getBean(ChatSessionStore.class);
        this.chatMessageService = SpringContext.getContext().getBean(ChatMessageService.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {

        chatSessionStore.addSession(sessionId, session);
        log.info("connected to session {} with current id {}", sessionId, session.getId());
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("sessionId") String sessionId, ChatMessageDTO message) {

        send(sessionId, session, message);
    }

    @OnClose
    public void onClose(Session session, @PathParam("sessionId") String sessionId) throws IOException {

        chatSessionStore.removeSession(sessionId, session);
        log.info("disconnected to session {} with current id {}", sessionId, session.getId());
    }

    @OnError
    public void onError(Session session, @PathParam("sessionId") String sessionId, Throwable throwable) {
        chatSessionStore.removeSession(sessionId, session);
        log.info("error in session {} with current id {}", sessionId, session.getId());
    }

    public void send(Session session, ChatMessageDTO message, String sessionId) {
        try {
            log.info("Sending messag");
            session.getBasicRemote().sendObject(message);
            chatMessageService.saveAsync(message, sessionId);
        } catch (Exception e) {
            log.error("WebSocket error, message {} was not sent. to session with id {}", message, session.getId());
        }
    }

    public void send(String sessionId, Session session, ChatMessageDTO message) {
        try {
            chatSessionStore.getAllSessionsExcept(sessionId, session).forEach(s -> send(s, message, sessionId));
        } catch (Exception e) {
            log.error("WebSocket error, message {} was not sent.", message, e);
        }
    }

}
