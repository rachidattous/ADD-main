package com.add.auth.controllers;

import java.util.Optional;

import org.keycloak.representations.AccessTokenResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.add.auth.dto.RefreshTokenDTO;
import com.add.auth.dto.SignInDTO;
import com.add.auth.services.AuthentificationService;

import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthentificationController {

  private final AuthentificationService authentificationService;

  @PostMapping("/login")
  public Optional<AccessTokenResponse> getToken(@RequestBody SignInDTO signInDTO) {

    return authentificationService.login(signInDTO);
  }

  @PostMapping("/refresh")
  public Optional<AccessTokenResponse> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {

    return authentificationService.refreshToken(refreshTokenDTO);
  }

  @DeleteMapping("/logout")
  public void logout(@RequestBody RefreshTokenDTO refreshTokenDTO) {

    authentificationService.logout(refreshTokenDTO);
  }

}
