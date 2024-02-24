package com.add.file.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.add.file.constants.ValidationType;
import com.add.file.util.DateUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Validation {

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private ValidationType validationType;

  private String comment;

  private Long validationOrder;

  private String userId;

  @Builder.Default
  private LocalDate date = DateUtility.nowDate();

  @Builder.Default
  private LocalTime time = DateUtility.nowTime();

  @JsonIgnore
  @ManyToOne
  private Content content;

}
