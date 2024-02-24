package com.add.communication.ws.decoder;

import lombok.RequiredArgsConstructor;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.add.communication.dto.ChatMessageDTO;

import com.add.communication.util.ParsingUtil;

@RequiredArgsConstructor
public class MessageDecoder implements Decoder.Text<ChatMessageDTO> {

    @Override
    public ChatMessageDTO decode(String message) {
        return ParsingUtil.GSON.fromJson(message, ChatMessageDTO.class);
    }

    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
