package com.add.communication.services;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.add.communication.dto.ChatMessageDTO;
import com.add.communication.model.ChatMessage;
import com.add.communication.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

  private final ChatMessageRepository chatMessageRepository;

  private final ModelMapper modelMapper;

  @Async
  public void saveAsync(ChatMessageDTO chatMessageDTO, String sessionId) {
    ChatMessage chat = modelMapper.map(chatMessageDTO, ChatMessage.class);
    chat.setSessionId(sessionId);
    chatMessageRepository.save(chat);
  }

}
