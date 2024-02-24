package com.add.communication.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private String senderId;

  private String reciverId;

  private String sessionId;

  private String content;

}
