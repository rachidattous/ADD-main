package com.add.auth.services;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.add.auth.dto.MailDTO;
import com.add.auth.exception.user.ExpiredCodeException;
import com.add.auth.exception.user.VerifyEmailException;
import com.add.auth.model.UserData;
import com.add.auth.model.VerifyEmail;
import com.add.auth.repository.VerifyEmailRepository;

import com.add.auth.util.Utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyEmailService {

  private final VerifyEmailRepository verifyEmailRepository;

  private final TemplateService templateService;

  private final RestService restService;

  private final Random random;

  private final JobService jobService;

  @Async
  public void sendVerificationEmail(UserData userData) {
    try {
      VerifyEmail verification = verifyEmailRepository
          .save(VerifyEmail.builder()
              .userId(userData.getId())
              .code(Utils.generateRandomStr(random, 64))
              .build());
      jobService.scheduleExpirOfEmailVerification(verification);
      MailDTO mail = new MailDTO();
      mail.setTo(Arrays.asList(userData.getEmail()));
      mail.setSubject("ADD-Elearning Email Verification");
      mail.setBody(templateService.verifyEmailTemplate(verification));
      restService.sendEmail(mail);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  public void deleteVerifyEmail(VerifyEmail verifyEmail) {
    Optional.ofNullable(verifyEmail)
        .ifPresent(verifyEmailRepository::delete);
  }

  public String verifyEmail(String code) {

    return verifyEmailRepository.findByCode(code)
        .map(this::verifyEmail)
        .orElseThrow(VerifyEmailException::new);

  }

  public String verifyEmail(VerifyEmail verifyEmail) {
    deleteVerifyEmail(verifyEmail);
    jobService.unschedule(verifyEmail.getId(), VerifyEmail.JOB_GROUP);
    if (verifyEmail.isExpired()) {
      throw new ExpiredCodeException();
    }

    return verifyEmail.getUserId();

  }

}
