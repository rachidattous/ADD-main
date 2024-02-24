package com.add.calendar.model.converter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DaysOfWeekConverter implements AttributeConverter<Set<DayOfWeek>, String> {

  private static final String SPLIT_CHAR = ",";

  @Override
  public String convertToDatabaseColumn(Set<DayOfWeek> days) {
    return days != null ? convertToDayOfWeeksString(days) : null;
  }

  @Override
  public Set<DayOfWeek> convertToEntityAttribute(String days) {
    return days != null ? convertToDayOfWeeks(days) : new HashSet<>();
  }

  public String convertToDayOfWeeksString(Set<DayOfWeek> days) {

    return days.stream().sorted().map(e -> String.valueOf(e.ordinal() + 1)).collect(Collectors.joining(SPLIT_CHAR));

  }

  public Set<DayOfWeek> convertToDayOfWeeks(String days) {

    return Arrays.asList(days.split(SPLIT_CHAR))
        .stream()
        .map(Integer::parseInt)
        .map(this::toDaysOfWeek)
        .sorted()
        .collect(Collectors.toSet());

  }

  private DayOfWeek toDaysOfWeek(Integer e) {
    return DayOfWeek.of(e);
  }
}