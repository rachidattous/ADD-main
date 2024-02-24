package com.add.auth.services;

import java.util.Optional;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.add.auth.constants.Constants;
import com.add.auth.dto.RefreshTokenDTO;

import com.add.auth.dto.SignInDTO;
import com.add.auth.exception.auth.LogOutException;
import com.add.auth.exception.auth.RefreshTokenException;
import com.add.auth.exception.auth.TokenException;

import com.add.auth.util.HttpEntityUtil;
import com.add.auth.util.StringTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthentificationService {

  @Value("${keycloak.credentials.secret}")
  private String secretKey;

  @Value("${keycloak.resource}")
  private String clientId;

  @Value("${keycloak.auth-server-url}")
  private String authUrl;

  @Value("${keycloak.realm}")
  private String realm;

  private final RestTemplate restTemplate;

  public Keycloak buildKeyClock(String username, String password) {

    return KeycloakBuilder.builder()
        .realm(realm)
        .clientId(clientId)
        .serverUrl(authUrl)
        .grantType(OAuth2Constants.PASSWORD)
        .clientSecret(secretKey)
        .username(username)
        .password(password)
        .build();
  }

  public Optional<AccessTokenResponse> login(SignInDTO signInDTO) {

    try {
      log.info("username : {} is trying to connect", signInDTO.getUsername());
      AccessTokenResponse accessTokenResponse = buildKeyClock(signInDTO.getUsername(), signInDTO.getPassword())
          .tokenManager()
          .getAccessToken();
      return Optional.of(accessTokenResponse);

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new TokenException();
    }

  }

  public void logout(RefreshTokenDTO refreshTokenDTO) {
    try {
      log.info("refresh token logging out : {} ", refreshTokenDTO.getRefreshToken());

      String url = StringTemplate.template("${authUrl}/realms/${realm}/protocol/openid-connect/logout")
          .addParameter(Constants.AUTH_URL, authUrl)
          .addParameter(Constants.REALM, realm)
          .build();

      HttpEntity<MultiValueMap<String, String>> entity = HttpEntityUtil.builder()
          .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
          .addParameter(Constants.CLIENT_ID, clientId)
          .addParameter(Constants.CLIENT_SECRET, secretKey)
          .addParameter(Constants.REFRESH_TOKEN, refreshTokenDTO.getRefreshToken())
          .build();

      restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new LogOutException();
    }
  }

  public Optional<AccessTokenResponse> refreshToken(RefreshTokenDTO refreshTokenDTO) {
    try {
      String url = StringTemplate.template("${authUrl}/realms/${realm}/protocol/openid-connect/token")
          .addParameter(Constants.AUTH_URL, authUrl)
          .addParameter(Constants.REALM, realm)
          .build();

      log.info("used url : {}", url);

      HttpEntity<MultiValueMap<String, String>> entity = HttpEntityUtil.builder()
          .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
          .addParameter(Constants.GRANT_TYPE, Constants.REFRESH_TOKEN)
          .addParameter(Constants.REFRESH_TOKEN, refreshTokenDTO.getRefreshToken())
          .addParameter(Constants.CLIENT_ID, clientId)
          .addParameter(Constants.CLIENT_SECRET, secretKey)
          .build();

      return Optional.of(restTemplate.exchange(url, HttpMethod.POST, entity, AccessTokenResponse.class))
          .filter(e -> e.getStatusCode().equals(HttpStatus.OK))
          .map(e -> e.getBody())
          .map(Optional::of)
          .orElseThrow(RefreshTokenException::new);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new RefreshTokenException();
    }
  }
}
