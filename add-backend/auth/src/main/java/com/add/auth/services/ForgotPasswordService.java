package com.add.auth.services;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.add.auth.dto.MailDTO;
import com.add.auth.exception.user.ExpiredCodeException;
import com.add.auth.exception.user.ForgotPasswordException;
import com.add.auth.model.UserData;
import com.add.auth.model.ForgotPassword;
import com.add.auth.repository.ForgotPasswordRepository;

import com.add.auth.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

  private final ForgotPasswordRepository forgotPasswordRepository;

  private final TemplateService templateService;

  private final RestService restService;

  private final Random random;

  private final JobService jobService;

  @Async
  public void sendForgotPasswordEmail(UserData userData) {
    try {
      ForgotPassword forgot = forgotPasswordRepository
          .save(ForgotPassword.builder()
              .userId(userData.getId())
              .code(Utils.generateRandomStr(random, 64))
              .build());
      jobService.scheduleExpireOfForgotPassword(forgot);
      MailDTO mail = new MailDTO();
      mail.setTo(Arrays.asList(userData.getEmail()));
      mail.setSubject("ADD-Elearning Forgot Password");
      mail.setBody(templateService.forgotPasswordTemplate(forgot));
      restService.sendEmail(mail);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  public void deleteForgotPassword(ForgotPassword ForgotPassword) {
    Optional.ofNullable(ForgotPassword)
        .ifPresent(forgotPasswordRepository::delete);
  }

  public String forgotPassword(String code) {

    return forgotPasswordRepository.findByCode(code)
        .map(this::forgotPassword)
        .orElseThrow(ForgotPasswordException::new);

  }

  public String forgotPassword(ForgotPassword forgotPassword) {
    deleteForgotPassword(forgotPassword);
    jobService.unschedule(forgotPassword.getId(), ForgotPassword.JOB_GROUP);
    if (forgotPassword.isExpired()) {
      throw new ExpiredCodeException();
    }

    return forgotPassword.getUserId();

  }

}
