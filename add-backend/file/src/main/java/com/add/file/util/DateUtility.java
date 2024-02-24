package com.add.file.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class DateUtility {

  public static final ZoneId zone = ZoneId.of("Africa/Casablanca");

  public static LocalDate nowDate() {

    return LocalDate.now(zone);

  }

  public static LocalTime nowTime() {

    return LocalTime.now(zone);

  }

  public static LocalDateTime nowDateTime() {

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
    return dates.stream().filter(Objects::nonNull).reduce(DateUtility::recentDate).orElse(defaultDate);

  }

  public static LocalDate oldestDate(LocalDate a, LocalDate b) {
    return (a.isBefore(b)) ? a : b;
  }

  public static LocalDate oldestDate(List<LocalDate> dates, LocalDate defaultDate) {
    return dates.stream().filter(Objects::nonNull).reduce(DateUtility::oldestDate).orElse(defaultDate);

  }

  public static boolean isBeforeOrEqual(LocalDate a, LocalDate b) {
    return a.isBefore(b) || a.isEqual(b);
  }

}
