package com.add.communication.util;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utils {

  public LocalDate parseDateImport(String date) {
    try {
      if (date == null) {
        return null;
      }

      return LocalDate.parse(date);
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }

  }

  public <T> boolean notNull(T element) {

    return element != null;

  }

  public <T> Set<T> addToSet(Set<T> set, T element) {

    return addToSet(set, new HashSet<T>(Arrays.asList(element)));

  }

  public <T> Set<T> addToSet(Set<T> set, Set<T> elements) {

    if (elements == null || elements.isEmpty()) {
      return set;
    }
    if (set == null || set.isEmpty()) {
      return elements;
    }
    return Stream.of(set, elements)
        .flatMap(Set::stream)
        .filter(e -> e != null).distinct()
        .collect(Collectors.toSet());

  }

  public <T> Set<T> removeFromSet(Set<T> Set, T element) {

    if (element == null) {
      return Set;
    }
    if (Set == null || Set.isEmpty()) {
      return new HashSet<>();
    }
    return Set.stream().filter(e -> e != null).filter(e -> !e.equals(element)).distinct().collect(Collectors.toSet());

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
      return new ArrayList<>();
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
