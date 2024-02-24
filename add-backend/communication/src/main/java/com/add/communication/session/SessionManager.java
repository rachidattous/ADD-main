package com.add.communication.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.websocket.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionManager {

  @Bean
  public ChatSessionStore getChatSessionStore() {
    return new ChatSessionStore(Collections.synchronizedMap(new HashMap<String, Set<Session>>()));
  }

  @Bean
  public ScreenShareSessionStore getScreenShareSessionStore() {
    return new ScreenShareSessionStore(Collections.synchronizedMap(new HashMap<String, Set<Session>>()));
  }

  @Bean
  public VideoChatSessionStore getVideoChatSessionStore() {
    return new VideoChatSessionStore(Collections.synchronizedMap(new HashMap<String, Set<Session>>()));
  }
}
