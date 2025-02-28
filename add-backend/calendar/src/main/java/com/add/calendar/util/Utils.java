package com.add.calendar.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Utils {

  public static boolean isHexColor(String color) {

    return (color != null && color.matches("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$"));
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
