package com.add.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.add.auth.model.ForgotPassword;
import com.add.auth.model.VerifyEmail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateService {

  @Value("${app.url}")
  private String appURL;

  private final TemplateEngine templateEngine;

  public String verifyEmailTemplate(VerifyEmail verifyEmail) {

    Context context = new Context();
    context.setVariable("url", appURL);
    context.setVariable("userId", verifyEmail.getUserId());
    context.setVariable("code", verifyEmail.getCode());
    context.setVariable("expiration", verifyEmail.getExpiration());
    return templateEngine.process("verifyEmail", context);

  }

  public String forgotPasswordTemplate(ForgotPassword forgotPassword) {

    Context context = new Context();
    context.setVariable("url", appURL);
    context.setVariable("userId", forgotPassword.getUserId());
    context.setVariable("code", forgotPassword.getCode());
    context.setVariable("expiration", forgotPassword.getExpiration());
    return templateEngine.process("forgotPassword", context);

  }

}
