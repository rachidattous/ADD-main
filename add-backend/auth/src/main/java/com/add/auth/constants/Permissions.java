package com.add.auth.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permissions implements IEnum {

  SHOW_PROFILE("SHOW_PROFILE"),

  SHOW_SETTINGS("SHOW_SETTINGS"),

  SHOW_HOME("SHOW_HOME"),

  SHOW_CALENDAR("SHOW_CALENDAR"),

  SHOW_USERS("SHOW_USERS"),

  SHOW_ROLES("SHOW_ROLES"),

  SHOW_PERMISSIONS("SHOW_PERMISSIONS"),

  SHOW_CONTENT_VIDEOS("SHOW_CONTENT_VIDEOS"),

  SHOW_CONTENT_FILES("SHOW_CONTENT_FILES"),

  SHOW_CONTENT_IMAGES("SHOW_CONTENT_IMAGES"),

  SHOW_CONTENT_AUDIOS("SHOW_CONTENT_AUDIOS"),

  SHOW_CONTENT_DOCUMENTS("SHOW_CONTENT_DOCUMENTS"),

  SHOW_COURSES_BUILDER_LIST("SHOW_COURSES_BUILDER_LIST"),

  SHOW_EVALUATE_CONTENT("SHOW_EVALUATE_CONTENT"),

  SHOW_REPLACE_CONTENT("SHOW_REPLACE_CONTENT"),

  NAN("NAN");

  private final String value;

  public static List<Permissions> getAllPermissions() {
    return Arrays.asList(values());
  }

  public static Permissions getPermissionByValue(String value) {
    return Optional.ofNullable(value)
        .map(val -> Arrays.asList(values())
            .stream()
            .filter(e -> !e.equals(NAN))
            .filter(e -> e.getValue().equals(val))
            .findFirst()
            .orElse(NAN))
        .orElse(NAN);

  }
}
