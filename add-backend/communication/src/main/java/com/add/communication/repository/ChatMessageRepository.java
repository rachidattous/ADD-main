package com.add.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.add.communication.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

}
