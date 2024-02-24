package com.add.calendar.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.add.calendar.dto.EventCreationDTO;
import com.add.calendar.dto.EventIntervalDTO;
import com.add.calendar.dto.EventUpdateDTO;
import com.add.calendar.dto.SchedulerParams;
import com.add.calendar.model.Event;
import com.add.calendar.repository.EventRepository;
import com.add.calendar.services.jobs.EventJob;
import com.add.calendar.util.DateUtility;
import com.add.calendar.util.StringTemplate;

import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;

  private final ModelMapper modelMapper;

  private final JobService jobService;

  public SchedulerParams<Event> toSchedulerParams(@Valid @NotNull Event event) {
    return SchedulerParams.<Event>builder()
        .object(event).jobClass(EventJob.class)
        .mapKey("event").jobId(event.getId()).group(Event.JOB_GROUP)
        .description("scheduel event")
        .startAt(DateUtility.closeToMinutes(DateUtility.getZoneDateTimeByLocalDateTime(event.getStart()), 10))
        .reccurent(event.isReccurent()).cron(eventStartToCron(event))
        .build();

  }

  public String eventStartToCron(@Valid @NotNull Event event) {
    return StringTemplate.template("${S} ${M} ${H} ? * ${Ds} *")
        .addParameter("S", String.valueOf(event.getStart().getSecond()))
        .addParameter("M", String.valueOf(event.getStart().getMinute()))
        .addParameter("H", String.valueOf(event.getStart().getHour()))
        .addParameter("Ds", event.daysToString())
        .build();

  }

  public void scheduelEventOnReboot() {
    eventRepository.findByStartGreaterThan(DateUtility.nowDateTime())
        .stream()
        .forEach(this::scheduelEvent);
  }

  public boolean scheduelEvent(@Valid @NotNull Event event) {

    return jobService.schedule(toSchedulerParams(event));
  }

  public boolean rescheduelEvent(@Valid @NotNull Event event) {

    return jobService.reschedule(toSchedulerParams(event));
  }

  public boolean unscheduelEvent(@Valid @NotNull Event event) {

    return jobService.unschedule(toSchedulerParams(event));
  }

  public Optional<Event> getById(@Valid @NotNull String id) {
    return eventRepository.findById(id);

  }

  public List<Event> getEventsByInterval(@Valid @NotNull EventIntervalDTO eventIntervalDTO) {
    return eventRepository.findByStartGreaterThanAndEndLessThan(eventIntervalDTO.getStart(),
        eventIntervalDTO.getEnd());

  }

  public Optional<Event> createEvent(@Valid @NotNull EventCreationDTO eventCreationDTO) {

    Event event = modelMapper.map(eventCreationDTO, Event.class);
    eventRepository.save(event);
    scheduelEvent(event);
    return Optional.of(event);

  }

  public Optional<Event> updateEvent(@Valid @NotNull String eventId, @Valid @NotNull EventUpdateDTO eventUpdateDTO) {
    return eventRepository.findById(eventId)
        .map(e -> updateEvent(e, eventUpdateDTO))
        .orElse(Optional.empty());

  }

  public Optional<Event> updateEvent(@Valid @NotNull Event event, @Valid @NotNull EventUpdateDTO eventUpdateDTO) {

    modelMapper.map(eventUpdateDTO, event);
    rescheduelEvent(event);
    return Optional.of(eventRepository.save(event));

  }

  public void deleteEvent(String id) {
    Optional.ofNullable(id)
        .map(eventRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .ifPresent(this::deleteEvent);

  }

  public void deleteEvent(@Valid @NotNull Event event) {

    eventRepository.delete(event);
    unscheduelEvent(event);

  }

}
