package com.add.auth.services;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import org.springframework.stereotype.Service;

import com.add.auth.exception.config.ApiException;
import com.add.auth.model.ForgotPassword;
import com.add.auth.model.VerifyEmail;
import com.add.auth.services.jobs.ForgotPasswordJob;
import com.add.auth.services.jobs.VerifyEmailJob;
import com.add.auth.util.DateUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final Scheduler scheduler;

    public void scheduleExpirOfEmailVerification(VerifyEmail verifyEmail) {
        try {
            ZonedDateTime dateTime = DateUtility.getZoneDateTimeByLocalDateTime(verifyEmail.getExpiration());
            if (dateTime.isBefore(DateUtility.nowZonedDateTime())) {
                log.info("dateTime must be after current time");
                return;
            }
            String desc = "Verification Email Expiration Delete";
            JobDetail jobDetail = buildJobDetail(verifyEmail, VerifyEmailJob.class, "verifyEmail", verifyEmail.getId(),
                    VerifyEmail.JOB_GROUP, desc);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime, VerifyEmail.JOB_GROUP, desc);
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void scheduleExpireOfForgotPassword(ForgotPassword forgotPassword) {
        try {
            ZonedDateTime dateTime = DateUtility.getZoneDateTimeByLocalDateTime(forgotPassword.getExpiration());
            if (dateTime.isBefore(DateUtility.nowZonedDateTime())) {
                log.info("dateTime must be after current time");
                return;
            }
            String desc = "forgot Password Expiration Delete";
            JobDetail jobDetail = buildJobDetail(forgotPassword, ForgotPasswordJob.class, "forgotPassword",
                    forgotPassword.getId(),
                    ForgotPassword.JOB_GROUP, desc);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime, VerifyEmail.JOB_GROUP, desc);
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public boolean unschedule(String jobId, String group) {
        try {
            Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobId, group));
            if (trigger != null) {

                scheduler.unscheduleJob(trigger.getKey());
                return true;
            } else {

                return false;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException(e.getMessage());
        }

    }

    public <T> JobDetail buildJobDetail(T object, Class<? extends Job> jobClass, String mapKey, String jobId,
            String group,
            String description) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(mapKey, object);
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobId, group)
                .withDescription(description)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt, String group, String description) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), group)
                .withDescription(description)
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}
