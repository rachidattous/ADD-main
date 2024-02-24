package com.add.auth.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {

  private List<String> to;

  private List<String> copy;

  private String subject;

  private String body;

  public List<String> getTo() {
    return Optional.ofNullable(to)
        .orElse(new ArrayList<>())
        .stream()
        .distinct()
        .collect(Collectors.toList());
  }

  public List<String> getCopy() {
    return Optional.ofNullable(copy)
        .orElse(new ArrayList<>())
        .stream()
        .distinct()
        .collect(Collectors.toList());
  }

}
