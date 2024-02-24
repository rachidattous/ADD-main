package com.add.course.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@UtilityClass
public class DateUtility {

  public static LocalDate nowDate() {

    ZoneId zone = ZoneId.of("Africa/Casablanca");
    return LocalDate.now(zone);

  }

  public static LocalTime nowTime() {

    ZoneId zone = ZoneId.of("Africa/Casablanca");
    return LocalTime.now(zone);

  }

  public static LocalDateTime nowDateTime() {

    ZoneId zone = ZoneId.of("Africa/Casablanca");
    return LocalDateTime.now(zone);

  }

  public static LocalDate getLastDayOfMonth(LocalDate date) {

    return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));

  }

  public static LocalDate getFirstDayOfMonth(LocalDate date) {

    return date.withDayOfMonth(1);

  }

  public static LocalDate recentDate(LocalDate a, LocalDate b) {
    return (a.isBefore(b)) ? b : a;
  }

  public static LocalDate recentDate(List<LocalDate> dates, LocalDate defaultDate) {
    return dates.stream().filter(d -> d != null).reduce((a, b) -> recentDate(a, b)).orElse(defaultDate);

  }

  public static LocalDate oldestDate(LocalDate a, LocalDate b) {
    return (a.isBefore(b)) ? a : b;
  }

  public static LocalDate oldestDate(List<LocalDate> dates, LocalDate defaultDate) {
    return dates.stream().filter(d -> d != null).reduce((a, b) -> oldestDate(a, b)).orElse(defaultDate);

  }

  public static boolean isBeforeOrEqual(LocalDate a, LocalDate b) {
    return a.isBefore(b) || a.isEqual(b);
  }

}
