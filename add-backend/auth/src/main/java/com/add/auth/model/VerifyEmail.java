package com.add.auth.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.add.auth.util.DateUtility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyEmail implements Serializable {

  public static final String JOB_GROUP = "VerifyEmailJob-jobs";

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  private String code;

  private String userId;

  @Builder.Default
  private LocalDateTime expiration = DateUtility.dateTimePlusDays(DateUtility.nowDateTime(), 2);

  public boolean isExpired() {
    return expiration.isBefore(DateUtility.nowDateTime());
  }

}
