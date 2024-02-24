package com.add.communication.session;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.websocket.Session;

import com.add.communication.exception.SessionNotExistException;
import com.add.communication.exception.SessionNullException;
import com.add.communication.exception.StoreNullException;
import com.add.communication.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ChatSessionStore {

  private final Map<String, Set<Session>> store;

  public Map<String, Set<Session>> getStore() {
    return store;
  }

  private void vaidateStore() {
    if (store == null) {
      throw new StoreNullException();
    }
  }

  private void vaidateSession(String sessionId, Session session) {
    vaidateSession(session);
    vaidateSession(sessionId);
  }

  private void vaidateSession(String sessionId) {
    if (sessionId == null) {
      throw new SessionNullException();
    }
  }

  private void vaidateSession(Session session) {
    if (session == null) {
      throw new SessionNullException();
    }
  }

  public void addSession(String sessionId, Session session) {
    vaidateStore();
    vaidateSession(sessionId, session);

    if (store.containsKey(sessionId)) {
      log.info("session {} already exist", sessionId);

      store.put(sessionId, Utils.addToSet(store.get(sessionId), session));
    } else {
      log.info("creating new session {}", sessionId);
      store.put(sessionId, new HashSet<>(Arrays.asList(session)));
    }
  }

  public void removeSession(String sessionId, Session session) {

    vaidateStore();
    vaidateSession(sessionId, session);

    if (!store.containsKey(sessionId)) {
      throw new SessionNotExistException(sessionId);
    }

    Set<Session> newSessions = Utils.removeFromSet(store.get(sessionId), session);
    if (newSessions.isEmpty()) {
      store.remove(sessionId);
    } else {
      store.put(sessionId, newSessions);
    }

  }

  public Set<Session> getAllSessionsExcept(String sessionId, Session session) {
    vaidateStore();
    vaidateSession(sessionId, session);
    return getStore()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().equals(sessionId))
        .map(Entry::getValue)
        .flatMap(Set::stream)
        .filter(s -> !s.equals(session))
        .collect(Collectors.toSet());

  }

  public Set<Session> getAllSessions(String sessionId) {
    vaidateSession(sessionId);
    return getStore()
        .entrySet()
        .stream()
        .filter(e -> e.getKey().equals(sessionId))
        .map(Entry::getValue)
        .flatMap(Set::stream)
        .collect(Collectors.toSet());

  }

}
