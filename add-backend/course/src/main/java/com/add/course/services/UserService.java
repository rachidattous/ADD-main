package com.add.course.services;

import org.springframework.stereotype.Service;

import java.util.Set;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private final KeycloakSecurityContext keycloakSecurityContext;

  public AccessToken getAccessToken() {

    return keycloakSecurityContext.getToken();

  }

  public Set<String> getRoles() {

    return getAccessToken().getRealmAccess().getRoles();

  }

}
