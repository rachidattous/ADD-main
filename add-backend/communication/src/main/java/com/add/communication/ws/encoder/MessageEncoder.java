package com.add.communication.ws.encoder;

import lombok.RequiredArgsConstructor;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.add.communication.dto.ChatMessageDTO;

import com.add.communication.util.ParsingUtil;

@RequiredArgsConstructor
public class MessageEncoder implements Encoder.Text<ChatMessageDTO> {

    @Override
    public String encode(ChatMessageDTO message) {

        return ParsingUtil.GSON.toJson(message);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
