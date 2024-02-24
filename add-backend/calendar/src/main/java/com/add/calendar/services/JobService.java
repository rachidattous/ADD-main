package com.add.calendar.services;

import org.quartz.CronScheduleBuilder;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.add.calendar.dto.SchedulerParams;
import com.add.calendar.exception.config.ApiException;

import com.add.calendar.util.DateUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class JobService {

    private final Scheduler scheduler;

    public <T> boolean schedule(@Valid @NotNull SchedulerParams<T> schedulerParams) {
        try {

            if (schedulerParams.getStartAt().isBefore(DateUtility.nowZonedDateTime())) {
                log.info("dateTime must be after current time");
                return false;
            }
            JobDetail jobDetail = buildJobDetail(schedulerParams);

            Trigger trigger = buildJobTrigger(jobDetail, schedulerParams);

            Date date = scheduler.scheduleJob(jobDetail, trigger);
            log.info("a new job is scheduled with jobId {} in the group {} that will start in {}",
                    schedulerParams.getJobId(), schedulerParams.getGroup(), date);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    public <T> boolean unschedule(@Valid @NotNull SchedulerParams<T> schedulerParams) {
        try {
            Trigger trigger = scheduler
                    .getTrigger(TriggerKey.triggerKey(schedulerParams.getJobId(), schedulerParams.getGroup()));
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

    public <T> boolean reschedule(@Valid @NotNull SchedulerParams<T> schedulerParams) {

        boolean unschedule = unschedule(schedulerParams);
        boolean scheduel = schedule(schedulerParams);

        return unschedule && scheduel;

    }

    public <T> JobDetail buildJobDetail(@Valid @NotNull SchedulerParams<T> schedulerParams) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(schedulerParams.getMapKey(), schedulerParams.getObject());
        return JobBuilder.newJob(schedulerParams.getJobClass())
                .withIdentity(schedulerParams.getJobId(), schedulerParams.getGroup())
                .withDescription(schedulerParams.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    public <T> Trigger buildJobTrigger(@Valid @NotNull JobDetail jobDetail,
            @Valid @NotNull SchedulerParams<T> schedulerParams) {
        TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(schedulerParams.getJobId(), schedulerParams.getGroup())
                .withDescription(schedulerParams.getDescription())
                .startAt(Date.from(schedulerParams.getStartAt().toInstant()));
        if (schedulerParams.isReccurent()) {
            builder.withSchedule(CronScheduleBuilder.cronSchedule(schedulerParams.getCron()));
        } else {
            builder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow());
        }

        return builder.build();
    }

}
