package com.add.auth.util;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utils {

  private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static String generateRandomStr(Random random, int length) {

    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return sb.toString();

  }

  public LocalDate parseDateImport(String date) {
    try {
      if (date == null) {
        return null;
      }
      // , DateTimeFormatter.ofPattern("dd/MM/yyyy")
      return LocalDate.parse(date);
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }

  }

  public boolean checkEmptyString(String s) {
    return s == null || s.trim().equals("");
  }

  public boolean checkEmptyStringList(List<String> list) {
    if (list == null || list.isEmpty()) {
      return true;
    }

    return list.stream().allMatch(Utils::checkEmptyString);

  }

  public <T> boolean notNull(T element) {

    return element != null;

  }

  public <T> boolean notNullandEmpty(List<T> element) {

    return element != null && !element.isEmpty();

  }

  public <T> List<T> addToList(List<T> list, T element) {

    return addToList(list, Arrays.asList(element));

  }

  public <T> List<T> addToList(List<T> list, List<T> elements) {

    if (elements == null || elements.isEmpty()) {
      return list;
    }
    if (list == null || list.isEmpty()) {
      return elements;
    }
    return Stream.of(list, elements).flatMap(List::stream).filter(e -> e != null).distinct()
        .collect(Collectors.toList());

  }

  public <T> List<T> removeFromList(List<T> list, T element) {

    if (element == null) {
      return list;
    }
    if (list == null || list.isEmpty()) {
      return new ArrayList<T>();
    }
    return list.stream().filter(e -> e != null).filter(e -> !e.equals(element)).distinct().collect(Collectors.toList());

  }

  public <T> UnaryOperator<T> peek(Consumer<T> c) {
    return x -> {
      c.accept(x);
      return x;
    };
  }

}
