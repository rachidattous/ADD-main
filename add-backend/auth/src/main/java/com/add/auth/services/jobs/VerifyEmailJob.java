package com.add.auth.services.jobs;

import java.util.Optional;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.add.auth.model.VerifyEmail;
import com.add.auth.repository.VerifyEmailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerifyEmailJob extends QuartzJobBean {

    private final VerifyEmailRepository verifyEmailRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        VerifyEmail verifyEmail = (VerifyEmail) jobDataMap.get("verifyEmail");

        Optional.ofNullable(verifyEmail).ifPresent(e -> {
            log.info("delete verifyEmailwith  form the scheduler with id  {}", e.getId());
            verifyEmailRepository.delete(e);
        });

    }

}
