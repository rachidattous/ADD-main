package com.add.forum.services;

import org.springframework.stereotype.Service;

import com.add.forum.model.User;
import com.add.forum.repository.UserRepository;

import java.util.Set;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private final KeycloakSecurityContext keycloakSecurityContext;

  private final UserRepository userRepository;

  public User getOrCreate(String userId) {

    return userRepository.findById(userId)
        .orElse(createUser(userId));

  }

  public User createUser(String userId) {

    return userRepository.save(User.builder().id(userId).username(userId).build());
  }

  public AccessToken getAccessToken() {

    return keycloakSecurityContext.getToken();

  }

  public Set<String> getRoles() {

    return getAccessToken().getRealmAccess().getRoles();

  }

}
