package com.add.auth.services.jobs;

import java.util.Optional;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.add.auth.model.ForgotPassword;
import com.add.auth.repository.ForgotPasswordRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForgotPasswordJob extends QuartzJobBean {

    private final ForgotPasswordRepository forgotPasswordRepository;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        ForgotPassword ForgotPassword = (ForgotPassword) jobDataMap.get("forgotPassword");

        Optional.ofNullable(ForgotPassword).ifPresent(e -> {
            log.info("delete ForgotPasswordwith  form the scheduler with id  {}", e.getId());
            forgotPasswordRepository.delete(e);
        });

    }

}
