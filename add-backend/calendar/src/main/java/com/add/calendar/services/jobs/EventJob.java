package com.add.calendar.services.jobs;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.add.calendar.model.Event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Component
public class EventJob extends QuartzJobBean {

    @Override
    protected void executeInternal(@Valid @NotNull JobExecutionContext jobExecutionContext)
            throws JobExecutionException {

        log.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        Event event = (Event) jobDataMap.get("event");

        log.info("eventId form the scheduler {}", event.getId());

    }

}
